package com.dsl.vpp.weight.service;

import com.dsl.vpp.generation.value.GenerationInfo;
import com.dsl.vpp.prediction.value.PredictionInfo;
import com.dsl.vpp.weight.value.WeightInfo;

import java.util.List;

public interface WeightService {
    void updateWeight(PredictionInfo predictionInfo, GenerationInfo generationInfo);
    Double calculateTrustRate(Double predictedAmount, Double generatedAmount);
    List<WeightInfo> getLatestWeightsByWindowSize(String derId, Integer windowSize);
}
