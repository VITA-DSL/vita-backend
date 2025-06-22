package com.dsl.vpp.settlementRecord.value;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
public class SettlementRecordInfo {
    String id;
    SettlementAmountInfo original;
    SettlementAmountInfo adjusted;
    LocalDateTime dateTime;
}
