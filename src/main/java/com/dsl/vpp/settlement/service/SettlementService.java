package com.dsl.vpp.settlement.service;

import com.dsl.vpp.generation.value.GenerationInfo;
import com.dsl.vpp.prediction.value.PredictionInfo;

public interface SettlementService {
    String settle(String derId, String generationId);
    Double calculateUnitPrice(GenerationInfo generationInfo, PredictionInfo predictionInfo);
}
