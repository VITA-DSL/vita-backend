package com.dsl.vpp.adjustedPrediction.service;

import com.dsl.vpp.adjustedPrediction.AdjustedPredictionEntity;
import com.dsl.vpp.adjustedPrediction.AdjustedPredictionMapper;
import com.dsl.vpp.adjustedPrediction.AdjustedPredictionRepository;
import com.dsl.vpp.adjustedPrediction.value.AdjustedPredictionInfo;
import com.dsl.vpp.adjustment.AdjustmentService;
import com.dsl.vpp.der.service.DerService;
import com.dsl.vpp.der.value.DerInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Service
public class AdjustedPredictionServiceImpl implements AdjustedPredictionService {
    private final DerService derService;
    private final AdjustmentService adjustmentService;
    private final AdjustedPredictionRepository adjustedPredictionRepository;

    @Override
    public void createTomorrowPredictions(String derId) {
        AdjustedPredictionInfo.AdjustedPredictionInfoBuilder adjustedPredictionInfoBuilder = AdjustedPredictionInfo.builder()
                .derId(derId);

        LocalDate date = LocalDate.now();

        for (int i=0;i<=23;i++) {
            LocalDateTime targetTime = LocalDateTime.of(date.getYear(), date.getMonth(), date.getDayOfMonth(), i, 0, 0, 0);
            Double amount = adjustmentService.adjust(derId, targetTime);

            AdjustedPredictionInfo adjustedPredictionInfo = adjustedPredictionInfoBuilder
                    .dateTime(targetTime)
                    .amount(amount)
                    .build();

            AdjustedPredictionEntity adjustedPredictionEntity = AdjustedPredictionMapper.mapToEntity(adjustedPredictionInfo);
            adjustedPredictionRepository.save(adjustedPredictionEntity);
        }
    }

    @Override
    public AdjustedPredictionInfo readById(String id) {
        return adjustedPredictionRepository.findById(id)
                .map(AdjustedPredictionMapper::mapToValue)
                .orElseThrow(()->new NoSuchElementException("존재하지 않는 보정 데이터입니다."));
    }

    @Override
    public List<AdjustedPredictionInfo> readByDerIdBetween(String derId, LocalDateTime start, LocalDateTime end) {
        return adjustedPredictionRepository.findByDerIdAndDateTimeBetween(derId, start, end).stream()
                .map(AdjustedPredictionMapper::mapToValue)
                .toList();
    }

    @Override
    public List<AdjustedPredictionInfo> readByVppIdBetween(String vppId, LocalDateTime start, LocalDateTime end) {
        List<String> derIds = derService.readByVppId(vppId).stream()
                .map(DerInfo::getId)
                .toList();

        return adjustedPredictionRepository.findByDerIdInAndDateTimeBetween(derIds, start, end).stream()
                .map(AdjustedPredictionMapper::mapToValue)
                .toList();
    }
}
