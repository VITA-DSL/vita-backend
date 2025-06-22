package com.dsl.vpp.prediction;

import com.dsl.vpp.prediction.dto.response.DailyPredictionGetListResponseDto;
import com.dsl.vpp.prediction.dto.response.DailyPredictionGetResponseDto;
import com.dsl.vpp.prediction.dto.response.PredictionGetListResponseDto;
import com.dsl.vpp.prediction.dto.response.PredictionGetResponseDto;
import com.dsl.vpp.prediction.dto.request.PredictionPostRequestDto;
import com.dsl.vpp.prediction.value.DailyPredictionInfo;
import com.dsl.vpp.prediction.value.PredictionInfo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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

    public static PredictionInfo mapToValue(PredictionEntity predictionEntity) {
        return PredictionInfo.builder()
                .id(predictionEntity.getId())
                .derId(predictionEntity.getDerId())
                .amount(predictionEntity.getAmount())
                .dateTime(predictionEntity.getDateTime())
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

    public static DailyPredictionInfo mapToValue(Map.Entry<LocalDate, Double> entry) {
        return DailyPredictionInfo.builder()
                .date(entry.getKey())
                .totalAmount(entry.getValue())
                .build();
    }

    public static DailyPredictionGetResponseDto mapToDto(DailyPredictionInfo dailyPredictionInfo) {
        return DailyPredictionGetResponseDto.builder()
                .date(dailyPredictionInfo.getDate())
                .totalAmount(dailyPredictionInfo.getTotalAmount())
                .build();
    }

    public static DailyPredictionGetListResponseDto mapDailyPredictionToDto(List<DailyPredictionInfo> dailyPredictions) {
        List<DailyPredictionGetResponseDto> dailyPredictionDtos = dailyPredictions.stream()
                .map(PredictionMapper::mapToDto)
                .toList();

        return DailyPredictionGetListResponseDto.builder()
                .predictions(dailyPredictionDtos)
                .build();
    }
}
