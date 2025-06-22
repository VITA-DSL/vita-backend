package com.dsl.vpp.prediction.service;

import com.dsl.vpp.prediction.value.DailyPredictionInfo;
import com.dsl.vpp.prediction.value.PredictionInfo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface PredictionService {
    String create(PredictionInfo predictionInfo);
    PredictionInfo readById(String predictionId);
    List<PredictionInfo> readByDerIdBetween(String derId, LocalDateTime start, LocalDateTime end);
    List<PredictionInfo> readMonthlyByDerId(String derId, Integer month);
    List<PredictionInfo> readByVppIdBetween(String vppId, LocalDateTime start, LocalDateTime end);
    List<DailyPredictionInfo> readDailyByVppIdBetween(String vppId, LocalDate start, LocalDate end);
    void validateIdExists(String id);
    void validateDateTime(LocalDateTime dateTime);
}
