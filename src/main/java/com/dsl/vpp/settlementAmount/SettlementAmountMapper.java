package com.dsl.vpp.settlementAmount;

import com.dsl.vpp.settlementAmount.dto.SettlementAmountGetListResponseDto;
import com.dsl.vpp.settlementAmount.dto.SettlementAmountGetResponseDto;
import com.dsl.vpp.settlementAmount.value.SettlementAmountInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SettlementAmountMapper {
    public static SettlementAmountEntity mapToEntity(SettlementAmountInfo settlementAmount) {
        return SettlementAmountEntity.builder()
                .generationId(settlementAmount.getGenerationId())
                .unitPrice(settlementAmount.getUnitPrice())
                .amount(settlementAmount.getAmount())
                .settledAt(settlementAmount.getSettledAt())
                .build();
    }
    public static SettlementAmountInfo mapToValue(SettlementAmountEntity settlementAmount) {
        return SettlementAmountInfo.builder()
                .id(settlementAmount.getId())
                .generationId(settlementAmount.getGenerationId())
                .unitPrice(settlementAmount.getUnitPrice())
                .amount(settlementAmount.getAmount())
                .settledAt(settlementAmount.getSettledAt())
                .build();
    }

    public static SettlementAmountGetResponseDto mapToDto(SettlementAmountInfo settlementAmount) {
        return SettlementAmountGetResponseDto.builder()
                .id(settlementAmount.getId())
                .generationId(settlementAmount.getGenerationId())
                .unitPrice(settlementAmount.getUnitPrice())
                .amount(settlementAmount.getAmount())
                .settledAt(settlementAmount.getSettledAt())
                .build();
    }
    public static SettlementAmountGetListResponseDto mapToDto(List<SettlementAmountInfo> settlementAmountInfoList) {
        ArrayList<SettlementAmountGetResponseDto> settlementAmounts = settlementAmountInfoList.stream()
                .map(SettlementAmountMapper::mapToDto)
                .collect(Collectors.toCollection(ArrayList::new));

        return SettlementAmountGetListResponseDto.builder()
                .settlementAmounts(settlementAmounts)
                .build();
    }
}
