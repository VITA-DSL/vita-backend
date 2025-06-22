package com.dsl.vpp.generation.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Builder
@Data
public class GenerationGetDailyResponseDto {
    Double totalAmount;
    LocalDate date;
}
