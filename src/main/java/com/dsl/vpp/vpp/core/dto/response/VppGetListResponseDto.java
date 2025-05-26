package com.dsl.vpp.vpp.core.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;

@Builder
@Data
public class VppGetListResponseDto {
    ArrayList<VppGetResponseDto> vpps;
}
