package com.dsl.vpp.settlement.service;

import com.dsl.vpp.settlementAmount.value.SettlementAmountInfo;

public interface SettlementService {
    SettlementAmountInfo simulateWithOriginalPrediction(String generationId);
    String settle(String generationId);
    Double calculateUnitPrice(Double generationAmount, Double errorRate);
}
