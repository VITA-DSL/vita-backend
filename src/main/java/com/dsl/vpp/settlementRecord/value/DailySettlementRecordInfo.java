package com.dsl.vpp.settlementRecord.value;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Builder
@Data
public class DailySettlementRecordInfo {
    Integer originalTotalAmount;
    Integer adjustedTotalAmount;
    LocalDate date;
}
