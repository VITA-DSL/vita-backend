package com.dsl.vpp.prediction.service;

import com.dsl.vpp.prediction.value.PredictionInfo;

import java.time.LocalDateTime;
import java.util.List;

public interface PredictionService {
    String create(PredictionInfo predictionInfo);
    PredictionInfo readById(String predictionId);
    List<PredictionInfo> readByDerIdBetween(String derId, LocalDateTime start, LocalDateTime end);
    List<PredictionInfo> readByVppIdBetween(String vppId, LocalDateTime start, LocalDateTime end);
    void validateIdExists(String id);
}
