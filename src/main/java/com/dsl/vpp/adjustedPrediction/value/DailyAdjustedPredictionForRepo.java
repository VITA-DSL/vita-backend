package com.dsl.vpp.adjustedPrediction.value;

import java.time.LocalDate;

public interface DailyAdjustedPredictionForRepo {
    Double getTotalAmount();
    LocalDate getDate();
}
