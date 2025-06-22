package com.dsl.vpp.generation.service;

import com.dsl.vpp.generation.value.DailyGenerationInfo;
import com.dsl.vpp.generation.value.GenerationInfo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface GenerationService {
    String create(GenerationInfo generation);
    GenerationInfo readById(String id);
    GenerationInfo readByPredictionId(String predictionId);
    List<GenerationInfo> readByDerIdBetween(String derId, LocalDateTime start, LocalDateTime end);
    List<DailyGenerationInfo> readDailyByVppIdBetween(String vppId, LocalDate start, LocalDate end);

    Double calculateOriginalErrorRate(String generationId);
    Double calculateAdjustedErrorRate(String generationId);
}
