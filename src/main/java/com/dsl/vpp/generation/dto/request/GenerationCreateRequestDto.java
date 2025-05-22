package com.dsl.vpp.generation.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
public class GenerationCreateRequestDto {
    @NotNull(message = "derId 는 필수 입력 값입니다.")
    String derId;
    @NotNull(message = "amount 는 필수 입력 값입니다.")
    Double amount;
    @NotNull(message = "dateTime 은 필수 입력 값입니다. 예시: 2025-05-22T12:00:00Z")
    LocalDateTime dateTime;
}
