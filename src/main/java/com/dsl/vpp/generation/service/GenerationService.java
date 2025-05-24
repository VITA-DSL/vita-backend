package com.dsl.vpp.generation.service;

import com.dsl.vpp.generation.value.GenerationInfo;

import java.time.LocalDateTime;
import java.util.List;

public interface GenerationService {
    String create(GenerationInfo generation);
    List<GenerationInfo> readByDerBetween(String derId, LocalDateTime start, LocalDateTime end);
    List<GenerationInfo> readByVppBetween(String vppId, LocalDateTime start, LocalDateTime end);
}
