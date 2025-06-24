package com.dsl.vpp.adjustedPrediction.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;

@Builder
@Data
public class AdjustedPredictionGetListResponseDto {
    ArrayList<AdjustedPredictionGetResponseDto> adjustedPredictions;

    @JsonProperty("adjusted-predictions")
    public ArrayList<AdjustedPredictionGetResponseDto> getAdjustedPredictions() {
        return adjustedPredictions;
    }
}
