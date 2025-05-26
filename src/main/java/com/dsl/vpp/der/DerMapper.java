package com.dsl.vpp.der;

import com.dsl.vpp.der.dto.request.DerPostRequestDto;
import com.dsl.vpp.der.dto.response.DerGetListResponseDto;
import com.dsl.vpp.der.dto.response.DerGetResponseDto;
import com.dsl.vpp.der.value.DerInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DerMapper {
    public static DerInfo mapToValue(DerPostRequestDto createRequestDto) {
        return DerInfo.builder()
                .capacity(createRequestDto.getCapacity())
                .build();
    }

    public static DerInfo mapToValue(DerEntity der) {
        return DerInfo.builder()
                .id(der.getId())
                .vppId(der.getVppId())
                .capacity(der.getCapacity())
                .build();
    }

    public static DerEntity mapToEntity(DerInfo der) {
        return DerEntity.builder()
                .capacity(der.getCapacity())
                .build();
    }

    public static DerGetResponseDto mapToDto(DerInfo derInfo) {
        return DerGetResponseDto.builder()
                .id(derInfo.getId())
                .vppId(derInfo.getVppId())
                .capacity(derInfo.getCapacity())
                .build();
    }

    public static DerGetListResponseDto mapToDto(List<DerInfo> derInfos) {
        ArrayList<DerGetResponseDto> ders = derInfos.stream()
                .map(DerMapper::mapToDto)
                .collect(Collectors.toCollection(ArrayList::new));

        return DerGetListResponseDto.builder()
                .ders(ders)
                .build();
    }
}
