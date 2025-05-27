package com.dsl.vpp.settlement.service;

import com.dsl.vpp.generation.service.GenerationService;
import com.dsl.vpp.generation.value.GenerationInfo;
import com.dsl.vpp.settlementAmount.service.SettlementAmountService;
import com.dsl.vpp.settlementAmount.value.SettlementAmountInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class SettlementServiceImpl implements SettlementService {
    private final GenerationService generationService;
    private final SettlementAmountService settlementAmountService;

    @Override
    public SettlementAmountInfo simulateWithOriginalPrediction(String generationId) {
        GenerationInfo generationInfo = generationService.readById(generationId);
        Double errorRate = generationService.calculateOriginalErrorRate(generationId);
        return settle(generationInfo, errorRate);
    }

    @Override
    public String settle(String generationId) {
        GenerationInfo generationInfo = generationService.readById(generationId);
        Double errorRate = generationService.calculateAdjustedErrorRate(generationId);
        SettlementAmountInfo settlementAmountInfo = settle(generationInfo, errorRate);
        return settlementAmountService.create(settlementAmountInfo);
    }

    private SettlementAmountInfo settle(GenerationInfo generationInfo, Double errorRate) {
        Double unitPrice = calculateUnitPrice(generationInfo.getAmount(), errorRate);
        return SettlementAmountInfo.builder()
                .generationId(generationInfo.getId())
                .unitPrice(unitPrice)
                .amount((int) (generationInfo.getAmount() * unitPrice))
                .settledAt(LocalDateTime.now())
                .build();
    }

    @Override
    public Double calculateUnitPrice(Double generationAmount, Double errorRate) {
        if (errorRate > 8.0) return 0.0;
        if (errorRate <= 6.0) return 4.0;
        return 3.0;
    }
}
