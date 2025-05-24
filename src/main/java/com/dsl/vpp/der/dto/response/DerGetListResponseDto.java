package com.dsl.vpp.der.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;

@Builder
@Data
public class DerGetListResponseDto {
    ArrayList<DerGetResponseDto> ders;
}
