package com.dsl.vpp.prediction.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
public class PredictionGetResponseDto {
    String id;
    String derId;
    Double amount;
    LocalDateTime dateTime;
}
