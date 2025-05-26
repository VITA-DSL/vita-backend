package com.dsl.vpp.settlement.service;

import com.dsl.vpp.generation.service.GenerationService;
import com.dsl.vpp.generation.value.GenerationInfo;
import com.dsl.vpp.prediction.service.PredictionService;
import com.dsl.vpp.prediction.value.PredictionInfo;
import com.dsl.vpp.settlementAmount.service.SettlementAmountService;
import com.dsl.vpp.settlementAmount.value.SettlementAmountInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class SettlementServiceImpl implements SettlementService {
    private final GenerationService generationService;
    private final PredictionService predictionService;
    private final SettlementAmountService settlementAmountService;

    @Override
    public String settle(String derId, String generationId) {
        GenerationInfo generationInfo = generationService.readById(generationId);
        PredictionInfo predictionInfo = predictionService.readById(generationInfo.getPredictionId());
        Double unitPrice = calculateUnitPrice(generationInfo, predictionInfo);

        SettlementAmountInfo settlementAmountInfo = SettlementAmountInfo.builder()
                .generationId(generationId)
                .unitPrice(unitPrice)
                .amount((int) (generationInfo.getAmount() * unitPrice))
                .settledAt(LocalDateTime.now())
                .build();

        return settlementAmountService.create(settlementAmountInfo);
    }

    @Override
    public Double calculateUnitPrice(GenerationInfo generationInfo, PredictionInfo predictionInfo) {
        return 5.0;
    }
}
