package com.dsl.vpp.adjustedPrediction.service;

import com.dsl.vpp.adjustedPrediction.AdjustedPredictionEntity;
import com.dsl.vpp.adjustedPrediction.AdjustedPredictionMapper;
import com.dsl.vpp.adjustedPrediction.AdjustedPredictionRepository;
import com.dsl.vpp.adjustedPrediction.value.AdjustedPredictionInfo;
import com.dsl.vpp.adjustment.AdjustmentService;
import com.dsl.vpp.der.service.DerService;
import com.dsl.vpp.der.value.DerInfo;
import com.dsl.vpp.prediction.service.PredictionService;
import com.dsl.vpp.prediction.value.PredictionInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Service
public class AdjustedPredictionServiceImpl implements AdjustedPredictionService {
    private final AdjustedPredictionRepository adjustedPredictionRepository;

    private final AdjustmentService adjustmentService;
    private final PredictionService predictionService;
    private final DerService derService;

    @Override
    public void generateAdjustedPredictionsByVppIdBetween(String vppId, LocalDateTime start, LocalDateTime end) {
        predictionService.readByVppIdBetween(vppId, start, end)
                .forEach(prediction -> {
                    if (!adjustedPredictionRepository.existsByDerIdAndDateTime(prediction.getDerId(), prediction.getDateTime())) {
                        generateAdjustedPrediction(prediction.getId());
                    }});
    }

    @Override
    public void generateAdjustedPrediction(String predictionId) {
        PredictionInfo prediction = predictionService.readById(predictionId);

        AdjustedPredictionInfo adjustedPrediction = AdjustedPredictionInfo.builder()
                .derId(prediction.getDerId())
                .amount(adjustmentService.adjust(prediction))
                .dateTime(prediction.getDateTime())
                .build();

        saveAdjustedPrediction(adjustedPrediction);
    }

    private void saveAdjustedPrediction(AdjustedPredictionInfo adjustedPrediction) {
        AdjustedPredictionEntity adjustedPredictionEntity = AdjustedPredictionMapper.mapToEntity(adjustedPrediction);

        adjustedPredictionRepository.save(adjustedPredictionEntity);
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
