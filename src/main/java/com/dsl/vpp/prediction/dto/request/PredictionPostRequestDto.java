package com.dsl.vpp.prediction.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
public class PredictionPostRequestDto {
    @NotNull(message = "amount 는 필수 입력 값입니다.")
    Double amount;
    @NotNull(message = "dateTime 은 필수 입력 값입니다.")
    LocalDateTime dateTime;
}
