package com.dsl.vpp.adjustedPrediction.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;

@Builder
@Data
public class AdjustedPredictionGetListResponseDto {
    @JsonProperty("adjusted-predictions")
    ArrayList<AdjustedPredictionGetResponseDto> adjustedPredictions;
}
