package com.dsl.vpp.generation.value;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Builder
@Data
public class DailyGenerationInfo {
    Double totalAmount;
    LocalDate date;
}
