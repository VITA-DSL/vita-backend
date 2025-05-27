package com.dsl.vpp.settlement.service;

import com.dsl.vpp.generation.service.GenerationService;
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
    public String settle(String generationId) {
        Double generatedAmount = generationService.readById(generationId).getAmount();
        Double errorRate = generationService.calculateAdjustedErrorRate(generationId);

        Double unitPrice = calculateUnitPrice(generatedAmount, errorRate);

        SettlementAmountInfo settlementAmountInfo = SettlementAmountInfo.builder()
                .generationId(generationId)
                .unitPrice(unitPrice)
                .amount((int) (generatedAmount * unitPrice))
                .settledAt(LocalDateTime.now())
                .build();

        return settlementAmountService.create(settlementAmountInfo);
    }

    @Override
    public Double calculateUnitPrice(Double generationAmount, Double errorRate) {
        if (errorRate > 8.0) return 0.0;
        if (errorRate <= 6.0) return 4.0;
        return 3.0;
    }
}
