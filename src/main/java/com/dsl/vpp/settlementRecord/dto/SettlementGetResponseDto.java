package com.dsl.vpp.settlementRecord.dto;

import com.dsl.vpp.settlementRecord.value.SettlementAmountInfo;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
public class SettlementGetResponseDto {
    String id;
    SettlementAmountInfo adjusted;
    SettlementAmountInfo original;
    LocalDateTime dateTime;
}
