package com.dsl.vpp.generation.value;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
public class GenerationInfo {
    String id;
    String predictionId;
    Double amount;
    LocalDateTime dateTime;
    LocalDateTime settledAt;
}
