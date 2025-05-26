package com.dsl.vpp.generation.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
public class GenerationGetResponseDto {
    String id;
    String predictionId;
    Double amount;
    LocalDateTime dateTime;
}
