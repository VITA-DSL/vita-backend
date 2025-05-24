package com.dsl.vpp.generation;

import com.dsl.vpp.der.DerEntity;
import com.dsl.vpp.generation.dto.request.GenerationCreateRequestDto;
import com.dsl.vpp.generation.dto.response.GenerationReadListResponseDto;
import com.dsl.vpp.generation.dto.response.GenerationReadResponseDto;
import com.dsl.vpp.generation.value.GenerationInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GenerationMapper {
    public static GenerationInfo mapToValue(String derId, GenerationCreateRequestDto createRequestDto) {
        return GenerationInfo.builder()
                .derId(derId)
                .amount(createRequestDto.getAmount())
                .dateTime(createRequestDto.getDateTime())
                .build();
    }
    public static GenerationInfo mapToValue(GenerationEntity generation) {
        return GenerationInfo.builder()
                .id(generation.getId())
                .derId(generation.getDer().getId())
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
                .der(DerEntity.builder().id(generation.getDerId()).build())
                .amount(generation.getAmount())
                .build();
    }
    public static GenerationReadResponseDto mapToDto(GenerationInfo generation) {
        return GenerationReadResponseDto.builder()
                .id(generation.getId())
                .derId(generation.getDerId())
                .amount(generation.getAmount())
                .dateTime(generation.getDateTime())
                .build();
    }
    public static GenerationReadListResponseDto mapToDto(List<GenerationInfo> generationInfos) {
        ArrayList<GenerationReadResponseDto> generations = generationInfos.stream()
                .map(GenerationMapper::mapToDto)
                .collect(Collectors.toCollection(ArrayList::new));

        return GenerationReadListResponseDto.builder()
                .generations(generations)
                .build();
    }
}
