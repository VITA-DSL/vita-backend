package com.dsl.vpp.generation;

import com.dsl.vpp.generation.dto.request.GenerationPostRequestDto;
import com.dsl.vpp.generation.dto.response.GenerationGetDailyListResponseDto;
import com.dsl.vpp.generation.dto.response.GenerationGetDailyResponseDto;
import com.dsl.vpp.generation.dto.response.GenerationGetListResponseDto;
import com.dsl.vpp.generation.dto.response.GenerationGetResponseDto;
import com.dsl.vpp.generation.value.DailyGenerationInfo;
import com.dsl.vpp.generation.value.GenerationInfo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GenerationMapper {
    public static GenerationInfo mapToValue(String predictionId, GenerationPostRequestDto createRequestDto) {
        return GenerationInfo.builder()
                .predictionId(predictionId)
                .amount(createRequestDto.getAmount())
                .dateTime(createRequestDto.getDateTime())
                .build();
    }
    public static GenerationInfo mapToValue(GenerationEntity generation) {
        return GenerationInfo.builder()
                .id(generation.getId())
                .predictionId(generation.getPredictionId())
                .amount(generation.getAmount())
                .dateTime(generation.getDateTime())
                .build();
    }

    public static DailyGenerationInfo mapToValue(Map.Entry<LocalDate, Double> entry) {
        return DailyGenerationInfo.builder()
                .date(entry.getKey())
                .totalAmount(entry.getValue())
                .build();
    }

    public static List<GenerationInfo> mapToValue(List<GenerationEntity> generations) {
        return generations.stream()
                .map(GenerationMapper::mapToValue)
                .collect(Collectors.toList());
    }

    public static GenerationEntity mapToEntity(GenerationInfo generation) {
        return GenerationEntity.builder()
                .predictionId(generation.getPredictionId())
                .amount(generation.getAmount())
                .dateTime(generation.getDateTime())
                .build();
    }

    public static GenerationGetDailyResponseDto mapToDto(DailyGenerationInfo dailyGeneration) {
        return GenerationGetDailyResponseDto.builder()
                .date(dailyGeneration.getDate())
                .totalAmount(dailyGeneration.getTotalAmount())
                .build();
    }

    public static GenerationGetDailyListResponseDto mapDailyGenerationToDto(List<DailyGenerationInfo> dailyGenerations) {
        List<GenerationGetDailyResponseDto> dailyGenerationDtos = dailyGenerations.stream()
                .map(GenerationMapper::mapToDto)
                .toList();

        return GenerationGetDailyListResponseDto.builder()
                .generations(dailyGenerationDtos)
                .build();
    }

    public static GenerationGetResponseDto mapToDto(GenerationInfo generation) {
        return GenerationGetResponseDto.builder()
                .id(generation.getId())
                .predictionId(generation.getPredictionId())
                .amount(generation.getAmount())
                .dateTime(generation.getDateTime())
                .build();
    }

    public static GenerationGetListResponseDto mapToDto(List<GenerationInfo> generationInfos) {
        ArrayList<GenerationGetResponseDto> generations = generationInfos.stream()
                .map(GenerationMapper::mapToDto)
                .collect(Collectors.toCollection(ArrayList::new));

        return GenerationGetListResponseDto.builder()
                .generations(generations)
                .build();
    }
}
