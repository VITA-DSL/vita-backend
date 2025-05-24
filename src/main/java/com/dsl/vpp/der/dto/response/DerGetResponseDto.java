package com.dsl.vpp.der.dto.response;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class DerGetResponseDto {
    String id;
    String vppId;
    Double capacity;
}
