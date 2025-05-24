package com.dsl.vpp.generation.service;

import com.dsl.vpp.generation.value.GenerationInfo;

import java.time.LocalDate;
import java.util.List;

public interface GenerationService {
    String create(GenerationInfo generation);
    List<GenerationInfo> readByDerBetween(String derId, LocalDate start, LocalDate end);
    List<GenerationInfo> readByVppBetween(String vppId, LocalDate start, LocalDate end);
}
