package com.dsl.vpp.prediction.service;

import com.dsl.vpp.der.service.DerService;
import com.dsl.vpp.der.value.DerInfo;
import com.dsl.vpp.prediction.PredictionEntity;
import com.dsl.vpp.prediction.PredictionMapper;
import com.dsl.vpp.prediction.PredictionRepository;
import com.dsl.vpp.prediction.value.DailyPredictionInfo;
import com.dsl.vpp.prediction.value.PredictionInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Service
public class PredictionServiceImpl implements PredictionService {
    private final PredictionRepository predictionRepository;
    private final DerService derService;

    @Override
    public String create(PredictionInfo predictionInfo) {
        derService.validateIdExists(predictionInfo.getDerId());
        PredictionEntity predictionEntity = PredictionMapper.mapToEntity(predictionInfo);
        return predictionRepository.save(predictionEntity).getId();
    }

    @Override
    public PredictionInfo readById(String id) {
        return predictionRepository.findById(id)
                .map(PredictionMapper::mapToValue)
                .orElseThrow(()->new NoSuchElementException("존재하지 않는 예측 데이터입니다."));
    }

    @Override
    public List<PredictionInfo> readByDerIdBetween(String derId, LocalDateTime start, LocalDateTime end) {
        return predictionRepository.findByDerIdAndDateTimeBetween(derId, start, end).stream()
                .map(PredictionMapper::mapToValue)
                .toList();
    }

    @Override
    public List<PredictionInfo> readMonthlyByDerId(String derId, Integer month) {
        return predictionRepository.findByMonth(month).stream()
                .map(PredictionMapper::mapToValue)
                .toList();
    }

    @Override
    public List<PredictionInfo> readByVppIdBetween(String vppId, LocalDateTime start, LocalDateTime end) {
        List<String> derIds = derService.readByVppId(vppId).stream()
                .map(DerInfo::getId)
                .toList();

        return predictionRepository.findByDerIdInAndDateTimeBetween(derIds, start, end).stream()
                .map(PredictionMapper::mapToValue)
                .toList();
    }

    @Override
    public List<DailyPredictionInfo> readDailyByVppIdBetween(String vppId, LocalDate start, LocalDate end) {
        List<String> derIds = derService.readByVppId(vppId).stream()
                .map(DerInfo::getId)
                .toList();

        return predictionRepository.findDailyByDerIdInAndDateTimeBetween(derIds, start.atStartOfDay(), end.atTime(23,59,59));
    }

    @Override
    public void validateIdExists(String id) {
        if(!predictionRepository.existsById(id)) {
            throw new NoSuchElementException("존재하지 않는 예측 데이터입니다.");
        }
    }

    @Override
    public void validateDateTime(LocalDateTime dateTime) {
        if(dateTime.getMinute() != 0 || dateTime.getSecond() != 0 || dateTime.getNano() == 0) {
            throw new IllegalArgumentException("예측 시간은 1시간 단위여야 합니다. 분, 초 단위가 존재해선 안됩니다.");
        }
    }
}
