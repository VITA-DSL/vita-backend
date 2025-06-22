package com.dsl.vpp.prediction.value;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Builder
@Data
public class DailyPredictionInfo {
    Double totalAmount;
    LocalDate date;
}
