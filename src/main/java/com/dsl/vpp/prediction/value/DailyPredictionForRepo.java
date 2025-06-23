package com.dsl.vpp.prediction.value;

import java.time.LocalDate;

public interface DailyPredictionForRepo {
    Double getTotalAmount();
    LocalDate getDate();
}
