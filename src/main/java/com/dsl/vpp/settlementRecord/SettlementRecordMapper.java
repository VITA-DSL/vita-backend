package com.dsl.vpp.settlementRecord;

import com.dsl.vpp.settlementRecord.dto.SettlementGetListResponseDto;
import com.dsl.vpp.settlementRecord.dto.SettlementGetResponseDto;
import com.dsl.vpp.settlementRecord.value.SettlementRecordInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SettlementRecordMapper {
    public static SettlementRecordEntity mapToEntity(SettlementRecordInfo settlement) {
        return SettlementRecordEntity.builder()
                .original(settlement.getOriginal())
                .adjusted(settlement.getAdjusted())
                .dateTime(settlement.getDateTime())
                .build();
    }
    public static SettlementRecordInfo mapToValue(SettlementRecordEntity settlement) {
        return SettlementRecordInfo.builder()
                .id(settlement.getId())
                .original(settlement.getOriginal())
                .adjusted(settlement.getAdjusted())
                .dateTime(settlement.getDateTime())
                .build();
    }
    public static SettlementGetResponseDto mapToDto(SettlementRecordInfo settlement) {
        return SettlementGetResponseDto.builder()
                .id(settlement.getId())
                .original(settlement.getOriginal())
                .adjusted(settlement.getAdjusted())
                .dateTime(settlement.getDateTime())
                .build();
    }
    public static SettlementGetListResponseDto mapToDto(List<SettlementRecordInfo> settlementRecordInfoList) {
        ArrayList<SettlementGetResponseDto> settlementAmounts = settlementRecordInfoList.stream()
                .map(SettlementRecordMapper::mapToDto)
                .collect(Collectors.toCollection(ArrayList::new));

        return SettlementGetListResponseDto.builder()
                .settlementAmounts(settlementAmounts)
                .build();
    }
}
