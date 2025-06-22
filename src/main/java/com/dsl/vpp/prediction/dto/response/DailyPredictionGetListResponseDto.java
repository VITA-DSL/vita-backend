package com.dsl.vpp.prediction.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class DailyPredictionGetListResponseDto {
    List<DailyPredictionGetResponseDto> predictions;
}
