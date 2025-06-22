package com.dsl.vpp.adjustedPrediction.value;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Builder
@Data
public class DailyAdjustedPredictionInfo {
    Double totalAmount;
    LocalDate date;
}
