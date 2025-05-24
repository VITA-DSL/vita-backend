package com.dsl.vpp.adjustedPrediction;

import com.dsl.vpp.adjustedPrediction.dto.AdjustedPredictionPostRequestDto;
import com.dsl.vpp.adjustedPrediction.dto.response.AdjustedPredictionGetListResponseDto;
import com.dsl.vpp.adjustedPrediction.dto.response.AdjustedPredictionGetResponseDto;
import com.dsl.vpp.adjustedPrediction.value.AdjustedPredictionInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AdjustedPredictionMapper {
    public static AdjustedPredictionInfo mapToValue(String derId, AdjustedPredictionPostRequestDto requestDto) {
        return AdjustedPredictionInfo.builder()
                .derId(derId)
                .amount(requestDto.getAmount())
                .dateTime(requestDto.getDateTime())
                .build();
    }
    public static AdjustedPredictionInfo mapToValue(AdjustedPredictionEntity adjustedPrediction) {
        return AdjustedPredictionInfo.builder()
                .id(adjustedPrediction.getId())
                .derId(adjustedPrediction.getDerId())
                .dateTime(adjustedPrediction.getDateTime())
                .build();
    }

    public static List<AdjustedPredictionInfo> mapToValue(List<AdjustedPredictionEntity> adjustedPredictionEntityList) {
        return adjustedPredictionEntityList.stream()
                .map(AdjustedPredictionMapper::mapToValue)
                .collect(Collectors.toList());
    }

    public static AdjustedPredictionEntity mapToEntity(AdjustedPredictionInfo adjustedPredictionInfo) {
        return AdjustedPredictionEntity.builder()
                .derId(adjustedPredictionInfo.getDerId())
                .amount(adjustedPredictionInfo.getAmount())
                .dateTime(adjustedPredictionInfo.getDateTime())
                .build();
    }
    public static AdjustedPredictionGetResponseDto mapToDto(AdjustedPredictionInfo adjustedPredictionInfo) {
        return AdjustedPredictionGetResponseDto.builder()
                .id(adjustedPredictionInfo.getId())
                .derId(adjustedPredictionInfo.getDerId())
                .amount(adjustedPredictionInfo.getAmount())
                .dateTime(adjustedPredictionInfo.getDateTime())
                .build();
    }
    public static AdjustedPredictionGetListResponseDto mapToDto(List<AdjustedPredictionInfo> adjustedPredictionInfoList) {
        ArrayList<AdjustedPredictionGetResponseDto> adjustedPredictions = adjustedPredictionInfoList.stream()
                .map(AdjustedPredictionMapper::mapToDto)
                .collect(Collectors.toCollection(ArrayList::new));

        return AdjustedPredictionGetListResponseDto.builder()
                .adjustedPredictions(adjustedPredictions)
                .build();
    }
}
