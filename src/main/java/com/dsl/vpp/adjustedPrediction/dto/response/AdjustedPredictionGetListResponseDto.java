package com.dsl.vpp.adjustedPrediction.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;

@Builder
@Data
public class AdjustedPredictionGetListResponseDto {
    ArrayList<AdjustedPredictionGetResponseDto> adjustedPredictions;
}
