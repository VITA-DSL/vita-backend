package com.dsl.vpp.prediction.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Builder
@Data
public class DailyPredictionGetResponseDto {
    Double totalAmount;
    LocalDate date;
}
