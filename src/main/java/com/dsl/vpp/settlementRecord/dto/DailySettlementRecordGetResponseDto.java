package com.dsl.vpp.settlementRecord.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Builder
@Data
public class DailySettlementRecordGetResponseDto {
    Integer originalTotalAmount;
    Integer adjustedTotalAmount;
    LocalDate date;
}
