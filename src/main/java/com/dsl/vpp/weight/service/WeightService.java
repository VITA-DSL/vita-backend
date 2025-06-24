package com.dsl.vpp.weight.service;

import com.dsl.vpp.weight.value.Weight;

import java.time.LocalDateTime;
import java.util.List;

public interface WeightService {
    void generateWeight(String generationId);
    void generateWeightsByVppIdBetween(String vppId, LocalDateTime start, LocalDateTime end);
    Double calculateTrustRate(Double predictedAmount, Double generatedAmount);
    List<Weight> getWeightsByWindowSize(String derId, LocalDateTime timestamp, Integer windowSize);
}
