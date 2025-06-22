package com.dsl.vpp.weight.service;

import java.time.LocalDateTime;
import java.util.List;

public interface WeightService {
    void generateWeight(String generationId);
    void generateWeightsByVppIdBetween(String vppId, LocalDateTime start, LocalDateTime end);
    Double calculateTrustRate(Double predictedAmount, Double generatedAmount);
    List<Double> getTrustRatesByWindowSize(String derId, LocalDateTime timestamp, Integer windowSize);
}
