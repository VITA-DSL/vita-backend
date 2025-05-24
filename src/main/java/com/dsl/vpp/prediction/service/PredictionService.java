package com.dsl.vpp.prediction.service;

import com.dsl.vpp.prediction.value.PredictionInfo;

import java.time.LocalDateTime;
import java.util.List;

public interface PredictionService {
    String create(PredictionInfo predictionInfo);
    PredictionInfo read(String predictionId);
    List<PredictionInfo> readByDerBetween(String derId, LocalDateTime start, LocalDateTime end);
    List<PredictionInfo> readByVppBetween(String vppId, LocalDateTime start, LocalDateTime end);
}
