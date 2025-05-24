package com.dsl.vpp.prediction.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;

@Builder
@Data
public class PredictionGetListResponseDto {
    ArrayList<PredictionGetResponseDto> predictions;
}
