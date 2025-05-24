package com.dsl.vpp.prediction.value;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
public class PredictionInfo {
    String id;
    String derId;
    Double amount;
    LocalDateTime dateTime;
}
