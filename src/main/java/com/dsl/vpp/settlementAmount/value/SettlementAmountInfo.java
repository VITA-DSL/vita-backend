package com.dsl.vpp.settlementAmount.value;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
public class SettlementAmountInfo {
    String id;
    String generationId;
    Double unitPrice;
    Integer amount;
    LocalDateTime settledAt;
}
