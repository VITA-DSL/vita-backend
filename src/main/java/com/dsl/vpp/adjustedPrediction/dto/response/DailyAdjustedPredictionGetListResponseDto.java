package com.dsl.vpp.adjustedPrediction.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class DailyAdjustedPredictionGetListResponseDto {
    List<DailyAdjustedPredictionGetResponseDto> adjustedPredictions;
}
