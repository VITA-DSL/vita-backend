package com.dsl.vpp.vpp.core.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class VppCreateRequestDto {
    @NotNull(message = "id 는 필수 입력 값입니다.")
    String id;
}
