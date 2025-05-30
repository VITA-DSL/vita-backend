package com.dsl.vpp.adjustedPrediction.service;

import com.dsl.vpp.adjustedPrediction.value.AdjustedPredictionInfo;

import java.time.LocalDateTime;
import java.util.List;

public interface AdjustedPredictionService {
    String create(AdjustedPredictionInfo adjustedPredictionInfo);
    AdjustedPredictionInfo readById(String id);
    List<AdjustedPredictionInfo> readByDerIdBetween(String derId, LocalDateTime start, LocalDateTime end);
    List<AdjustedPredictionInfo> readByVppIdBetween(String vppId, LocalDateTime start, LocalDateTime end);
}
