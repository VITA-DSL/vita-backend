package com.dsl.vpp.der;

import com.dsl.vpp.der.dto.request.DerCreateRequestDto;
import com.dsl.vpp.der.dto.response.DerReadListResponseDto;
import com.dsl.vpp.der.dto.response.DerReadResponseDto;
import com.dsl.vpp.der.value.DerInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DerMapper {
    public static DerInfo mapToValue(DerCreateRequestDto createRequestDto) {
        return DerInfo.builder()
                .capacity(createRequestDto.getCapacity())
                .build();
    }

    public static DerInfo mapToValue(DerEntity der) {
        return DerInfo.builder()
                .id(der.getId())
                .vppId(der.getVpp() != null ? der.getVpp().getId() : null)
                .capacity(der.getCapacity())
                .build();
    }

    public static List<DerInfo> mapToValue(List<DerEntity> ders) {
        return ders.stream()
                .map(DerMapper::mapToValue)
                .collect(Collectors.toList());
    }

    public static DerEntity mapToEntity(DerInfo der) {
        return DerEntity.builder()
                .capacity(der.getCapacity())
                .build();
    }

    public static DerReadResponseDto mapToDto(DerInfo derInfo) {
        return DerReadResponseDto.builder()
                .id(derInfo.getId())
                .vppId(derInfo.getVppId())
                .capacity(derInfo.getCapacity())
                .build();
    }

    public static DerReadListResponseDto mapToDto(List<DerInfo> ders) {
        return DerReadListResponseDto.builder()
                .ders(ders.stream()
                        .map(DerMapper::mapToDto)
                        .collect(Collectors.toCollection(ArrayList::new))
                )
                .build();
    }
}
