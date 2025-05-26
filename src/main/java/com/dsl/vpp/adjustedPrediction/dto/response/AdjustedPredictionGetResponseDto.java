package com.dsl.vpp.adjustedPrediction.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
public class AdjustedPredictionGetResponseDto {
    String id;
    String predictionId;
    Double amount;
    LocalDateTime dateTime;
}
