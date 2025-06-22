package com.dsl.vpp.adjustedPrediction.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Builder
@Data
public class DailyAdjustedPredictionGetResponseDto {
    Double totalAmount;
    LocalDate date;
}
