package com.dsl.vpp.vpp.core;

import com.dsl.vpp.vpp.core.dto.request.VppPostRequestDto;
import com.dsl.vpp.vpp.core.dto.response.VppGetListResponseDto;
import com.dsl.vpp.vpp.core.dto.response.VppGetResponseDto;
import com.dsl.vpp.vpp.core.value.VppInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class VppMapper {
    public static VppInfo mapToValue(VppPostRequestDto vppCreateRequest) {
        return VppInfo.builder()
                .id(vppCreateRequest.getId())
                .build();
    }

    public static VppInfo mapToValue(VppEntity vppEntity) {
        return VppInfo.builder()
                .id(vppEntity.getId())
                .build();
    }

    public static VppEntity mapToEntity(VppInfo vpp) {
        return VppEntity.builder()
                .id(vpp.getId())
                .build();
    }

    public static VppGetResponseDto mapToDto(VppInfo vpp) {
        return VppGetResponseDto.builder()
                .id(vpp.getId())
                .build();
    }

    public static VppGetListResponseDto mapToDto(List<VppInfo> vppInfos) {
        ArrayList<VppGetResponseDto> vpps = vppInfos.stream()
                .map(VppMapper::mapToDto)
                .collect(Collectors.toCollection(ArrayList::new));

        return VppGetListResponseDto.builder()
                .vpps(vpps)
                .build();
    }
}