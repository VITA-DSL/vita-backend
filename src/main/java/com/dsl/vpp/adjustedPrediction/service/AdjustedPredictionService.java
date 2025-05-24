package com.dsl.vpp.adjustedPrediction.service;

import com.dsl.vpp.adjustedPrediction.value.AdjustedPredictionInfo;

import java.time.LocalDateTime;
import java.util.List;

public interface AdjustedPredictionService {
    String create(AdjustedPredictionInfo adjustedPredictionInfo);
    List<AdjustedPredictionInfo> readByDerBetween(String derId, LocalDateTime start, LocalDateTime end);
    List<AdjustedPredictionInfo> readByVppBetween(String vppId, LocalDateTime start, LocalDateTime end);
}
