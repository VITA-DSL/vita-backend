package com.dsl.vpp.settlementRecord.value;

import jakarta.persistence.Embeddable;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@Embeddable
public class SettlementAmountInfo {
    Double unitPrice;
    Double power;
    Integer amount;
}
