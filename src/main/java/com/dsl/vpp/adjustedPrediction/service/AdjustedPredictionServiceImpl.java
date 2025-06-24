package com.dsl.vpp.adjustedPrediction.service;

import com.dsl.vpp.adjustedPrediction.AdjustedPredictionMapper;
import com.dsl.vpp.adjustedPrediction.AdjustedPredictionRepository;
import com.dsl.vpp.adjustedPrediction.value.AdjustedPredictionInfo;
import com.dsl.vpp.adjustedPrediction.value.DailyAdjustedPredictionInfo;
import com.dsl.vpp.adjustment.AdjustmentService;
import com.dsl.vpp.der.service.DerService;
import com.dsl.vpp.der.value.DerInfo;
import com.dsl.vpp.prediction.service.PredictionService;
import com.dsl.vpp.prediction.value.PredictionInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
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

    @Transactional
    @Override
    public void generateAdjustedPredictionsByVppIdBetween(String vppId, LocalDateTime start, LocalDateTime end) {
        predictionService.readByVppIdBetween(vppId, start, end)
                .forEach(p -> generateAdjustedPrediction(p.getId()));
    }

    @Transactional
    @Override
    public void generateAdjustedPrediction(String predictionId) {
        PredictionInfo prediction = predictionService.readById(predictionId);

        AdjustedPredictionInfo adjustedPrediction = AdjustedPredictionInfo.builder()
                .id(prediction.getId())
                .derId(prediction.getDerId())
                .amount(adjustmentService.adjust(prediction))
                .dateTime(prediction.getDateTime())
                .build();

        saveAdjustedPrediction(adjustedPrediction);
    }

    @Transactional
    private void saveAdjustedPrediction(AdjustedPredictionInfo adjustedPrediction) {
        adjustedPredictionRepository.insertIgnore(
                adjustedPrediction.getId(),
                adjustedPrediction.getDerId(),
                adjustedPrediction.getAmount(),
                adjustedPrediction.getDateTime()
        );
    }

    @Override
    public AdjustedPredictionInfo readById(String id) {
        return adjustedPredictionRepository.findById(id)
                .map(AdjustedPredictionMapper::mapToValue)
                .orElseThrow(()->new NoSuchElementException("존재하지 않는 보정 데이터입니다."));
    }

    @Override
    public List<AdjustedPredictionInfo> readByIds(List<String> predictionIds) {
        return adjustedPredictionRepository.findAllById(predictionIds)
                .stream()
                .map(AdjustedPredictionMapper::mapToValue)
                .toList();
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

    @Override
    public List<DailyAdjustedPredictionInfo> readDailyByVppIdBetween(String vppId, LocalDate start, LocalDate end) {
        List<String> derIds = derService.readByVppId(vppId).stream()
                .map(DerInfo::getId)
                .toList();

        return adjustedPredictionRepository.findDailyByDerIdInAndDateTimeBetween(derIds, start.atStartOfDay(), end.atTime(23,59,59))
                .stream()
                .map(AdjustedPredictionMapper::mapToValue)
                .toList();
    }
}
