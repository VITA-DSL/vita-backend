package com.dsl.vpp.adjustment.service;

import com.dsl.vpp.prediction.value.PredictionInfo;

public interface AdjustmentService {
    Double adjust(PredictionInfo predictionInfo);
    Double calculateWeightedPredictionAt(String derId, Integer t);
    Double calculateTrustRate(Double prediction, Double observation);
}
