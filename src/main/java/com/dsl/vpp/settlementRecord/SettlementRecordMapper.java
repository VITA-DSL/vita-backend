package com.dsl.vpp.settlementRecord;

import com.dsl.vpp.settlementRecord.dto.DailySettlementRecordGetListResponseDto;
import com.dsl.vpp.settlementRecord.dto.DailySettlementRecordGetResponseDto;
import com.dsl.vpp.settlementRecord.dto.SettlementGetListResponseDto;
import com.dsl.vpp.settlementRecord.dto.SettlementGetResponseDto;
import com.dsl.vpp.settlementRecord.value.DailySettlementRecordInfo;
import com.dsl.vpp.settlementRecord.value.SettlementRecordInfo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
    public static DailySettlementRecordInfo mapToValue(Map.Entry<LocalDate, Map<String, Integer>> entry) {
        LocalDate date = entry.getKey();
        Map<String, Integer> dailySettlement = entry.getValue();
        return DailySettlementRecordInfo.builder()
                .date(date)
                .originalTotalAmount(dailySettlement.get("original"))
                .adjustedTotalAmount(dailySettlement.get("adjusted"))
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

    public static DailySettlementRecordGetResponseDto mapToDailySettlementDto(DailySettlementRecordInfo dailySettlement) {
        return DailySettlementRecordGetResponseDto.builder()
                .originalTotalAmount(dailySettlement.getOriginalTotalAmount())
                .adjustedTotalAmount(dailySettlement.getAdjustedTotalAmount())
                .date(dailySettlement.getDate())
                .build();
    }

    public static DailySettlementRecordGetListResponseDto mapToDailySettlementListDto(List<DailySettlementRecordInfo> dailySettlements) {
        List<DailySettlementRecordGetResponseDto> dailySettlementDtos = dailySettlements.stream()
                .map(SettlementRecordMapper::mapToDailySettlementDto)
                .toList();
        return DailySettlementRecordGetListResponseDto.builder()
                .settlements(dailySettlementDtos)
                .build();
    }
}
