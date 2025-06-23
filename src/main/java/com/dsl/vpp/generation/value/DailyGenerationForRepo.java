package com.dsl.vpp.generation.value;

import java.time.LocalDate;

public interface DailyGenerationForRepo {
    Double getTotalAmount();
    LocalDate getDate();
}
