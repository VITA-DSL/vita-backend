package com.dsl.vpp.der.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class DerCreateRequestDto {
    @NotNull(message = "capacity 는 필수 입력 값입니다.")
    Double capacity;
}
