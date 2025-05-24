package com.dsl.vpp.prediction;

import com.dsl.vpp.prediction.dto.response.PredictionGetListResponseDto;
import com.dsl.vpp.prediction.dto.response.PredictionGetResponseDto;
import com.dsl.vpp.prediction.dto.request.PredictionPostRequestDto;
import com.dsl.vpp.prediction.value.PredictionInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PredictionMapper {
    public static PredictionEntity mapToEntity(PredictionInfo prediction) {
        return PredictionEntity.builder()
                .id(prediction.getId())
                .derId(prediction.getDerId())
                .amount(prediction.getAmount())
                .dateTime(prediction.getDateTime())
                .build();
    }

    public static PredictionInfo mapToValue(String derId, PredictionPostRequestDto requestDto) {
        return PredictionInfo.builder()
                .derId(derId)
                .amount(requestDto.getAmount())
                .dateTime(requestDto.getDateTime())
                .build();
    }

    public static PredictionGetResponseDto mapToDto(PredictionInfo predictionInfo) {
        return PredictionGetResponseDto.builder()
                .id(predictionInfo.getId())
                .derId(predictionInfo.getDerId())
                .amount(predictionInfo.getAmount())
                .dateTime(predictionInfo.getDateTime())
                .build();
    }

    public static PredictionGetListResponseDto mapToDto(List<PredictionInfo> predictionInfoList) {
        ArrayList<PredictionGetResponseDto> predictions = predictionInfoList.stream()
                .map(PredictionMapper::mapToDto)
                .collect(Collectors.toCollection(ArrayList::new));

        return PredictionGetListResponseDto.builder()
                .predictions(predictions)
                .build();
    }

    private static PredictionInfo mapToValue(PredictionEntity predictionEntity) {
        return PredictionInfo.builder()
                .id(predictionEntity.getId())
                .derId(predictionEntity.getDerId())
                .amount(predictionEntity.getAmount())
                .dateTime(predictionEntity.getDateTime())
                .build();
    }

    public static List<PredictionInfo> mapToValue(List<PredictionEntity> predictionEntityList) {
        return predictionEntityList.stream()
                .map(PredictionMapper::mapToValue)
                .collect(Collectors.toList());
    }
}
