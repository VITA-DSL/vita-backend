package com.dsl.vpp.weight.service;

import com.dsl.vpp.generation.value.GenerationInfo;
import com.dsl.vpp.prediction.value.PredictionInfo;

import java.time.LocalDateTime;
import java.util.List;

public interface WeightService {
    void updateWeight(PredictionInfo predictionInfo, GenerationInfo generationInfo);
    Double calculateTrustRate(Double predictedAmount, Double generatedAmount);
    List<Double> getTrustRatesByWindowSize(String derId, LocalDateTime timestamp, Integer windowSize);
}
