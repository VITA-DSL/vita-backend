package com.dsl.vpp.generation.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;

@Builder
@Data
public class GenerationGetListResponseDto {
    ArrayList<GenerationGetResponseDto> generations;
}
