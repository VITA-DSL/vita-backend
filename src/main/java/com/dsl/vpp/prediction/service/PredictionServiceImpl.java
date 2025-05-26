package com.dsl.vpp.prediction.service;

import com.dsl.vpp.der.service.DerService;
import com.dsl.vpp.der.value.DerInfo;
import com.dsl.vpp.prediction.PredictionEntity;
import com.dsl.vpp.prediction.PredictionMapper;
import com.dsl.vpp.prediction.PredictionRepository;
import com.dsl.vpp.prediction.value.PredictionInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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
    public PredictionInfo read(String id) {
        PredictionEntity predictionEntity = predictionRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("존재하지 않는 예측 데이터입니다."));
        return PredictionMapper.mapToValue(predictionEntity);
    }

    @Override
    public List<PredictionInfo> readByDerIdBetween(String derId, LocalDateTime start, LocalDateTime end) {
        List<PredictionEntity> predictionEntityList = predictionRepository.findByDerIdAndDateTimeBetween(derId, start, end);
        return PredictionMapper.mapToValue(predictionEntityList);
    }

    @Override
    public List<PredictionInfo> readByVppIdBetween(String vppId, LocalDateTime start, LocalDateTime end) {
        List<DerInfo> derInfoList = derService.readByVppId(vppId);
        return derInfoList.stream()
                .flatMap(derInfo -> readByDerIdBetween(derInfo.getId(), start, end).stream())
                .collect(Collectors.toList());
    }

    @Override
    public void validateIdExists(String id) {
        if(!predictionRepository.existsById(id)) {
            throw new IllegalArgumentException("존재하지 않는 예측 데이터입니다.");
        }
    }
}
