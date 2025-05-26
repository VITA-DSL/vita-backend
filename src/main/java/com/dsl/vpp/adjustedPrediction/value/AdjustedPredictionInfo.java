package com.dsl.vpp.adjustedPrediction.value;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
public class AdjustedPredictionInfo {
    String id;
    String predictionId;
    Double amount;
    LocalDateTime dateTime;
}
