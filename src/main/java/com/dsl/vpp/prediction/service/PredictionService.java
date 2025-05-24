package com.dsl.vpp.prediction.service;

import com.dsl.vpp.prediction.value.PredictionInfo;

import java.time.LocalDateTime;
import java.util.List;

public interface PredictionService {
    String create(PredictionInfo predictionInfo);
    List<PredictionInfo> readByDerBetween(String derId, LocalDateTime start, LocalDateTime end);
}
