package com.dsl.vpp.settlementAmount.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
public class SettlementAmountGetResponseDto {
    String id;
    String generationId;
    Double unitPrice;
    Integer amount;
    LocalDateTime settledAt;
}
