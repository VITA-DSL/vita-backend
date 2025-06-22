package com.dsl.vpp.settlement.service;

import com.dsl.vpp.adjustedPrediction.service.AdjustedPredictionService;
import com.dsl.vpp.adjustedPrediction.value.AdjustedPredictionInfo;
import com.dsl.vpp.generation.service.GenerationService;
import com.dsl.vpp.generation.value.GenerationInfo;
import com.dsl.vpp.prediction.service.PredictionService;
import com.dsl.vpp.prediction.value.PredictionInfo;
import com.dsl.vpp.settlementRecord.service.SettlementRecordService;
import com.dsl.vpp.settlementRecord.value.SettlementRecordInfo;
import com.dsl.vpp.settlementRecord.value.SettlementAmountInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class SettlementServiceImpl implements SettlementService {
    private final GenerationService generationService;
    private final PredictionService predictionService;
    private final AdjustedPredictionService adjustedPredictionService;
    private final SettlementRecordService settlementRecordService;

    @Override
    public String settle(String generationId) {
        GenerationInfo generation = generationService.readById(generationId);
        PredictionInfo prediction = predictionService.readById(generation.getPredictionId());
        AdjustedPredictionInfo adjustedPrediction = adjustedPredictionService.readById(generation.getPredictionId());

        SettlementAmountInfo original = createSettlementAmount(generation.getAmount(), prediction.getAmount());
        SettlementAmountInfo adjusted = createSettlementAmount(generation.getAmount(), adjustedPrediction.getAmount());
        LocalDateTime dateTime = generation.getDateTime();

        SettlementRecordInfo settlementRecordInfo = SettlementRecordInfo.builder()
                .original(original)
                .adjusted(adjusted)
                .dateTime(dateTime)
                .build();

        return settlementRecordService.create(settlementRecordInfo);
    }

    @Override
    public void settleByVppIdBetween(String vppId, LocalDateTime start, LocalDateTime end) {
        generationService.readByVppIdBetween(vppId, start, end)
                .forEach(generation -> {
                    settle(generation.getId());
                });
    }

    @Override
    public Double calculateUnitPrice(Double observation, Double prediction) {
        double errorRate = calculateErrorRate(observation, prediction);
        if (errorRate > 8.0) return 0.0;
        if (errorRate <= 6.0) return 4.0;
        return 3.0;
    }

    private SettlementAmountInfo createSettlementAmount(Double observation, Double prediction) {
        double unitPrice = calculateUnitPrice(observation, prediction);
        return SettlementAmountInfo.builder()
                .unitPrice(unitPrice)
                .power(observation)
                .amount((int)(unitPrice*observation))
                .build();
    }

    private Double calculateErrorRate(Double observation, Double prediction) {
        return ((observation - prediction) / observation) * 100.0;
    }
}
