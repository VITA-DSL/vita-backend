package com.dsl.vpp.settlementRecord.value;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class SettlementAmountInfo {
    Double unitPrice;
    Double power;
    Integer amount;
}
