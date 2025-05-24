package com.dsl.vpp.generation;

import com.dsl.vpp.generation.dto.request.GenerationPostRequestDto;
import com.dsl.vpp.generation.dto.response.GenerationGetListResponseDto;
import com.dsl.vpp.generation.dto.response.GenerationGetResponseDto;
import com.dsl.vpp.generation.value.GenerationInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GenerationMapper {
    public static GenerationInfo mapToValue(String derId, GenerationPostRequestDto createRequestDto) {
        return GenerationInfo.builder()
                .derId(derId)
                .amount(createRequestDto.getAmount())
                .dateTime(createRequestDto.getDateTime())
                .build();
    }
    public static GenerationInfo mapToValue(GenerationEntity generation) {
        return GenerationInfo.builder()
                .id(generation.getId())
                .derId(generation.getDerId())
                .dateTime(generation.getDateTime())
                .build();
    }

    public static List<GenerationInfo> mapToValue(List<GenerationEntity> generations) {
        return generations.stream()
                .map(GenerationMapper::mapToValue)
                .collect(Collectors.toList());
    }

    public static GenerationEntity mapToEntity(GenerationInfo generation) {
        return GenerationEntity.builder()
                .derId(generation.getDerId())
                .amount(generation.getAmount())
                .dateTime(generation.getDateTime())
                .build();
    }
    public static GenerationGetResponseDto mapToDto(GenerationInfo generation) {
        return GenerationGetResponseDto.builder()
                .id(generation.getId())
                .derId(generation.getDerId())
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
