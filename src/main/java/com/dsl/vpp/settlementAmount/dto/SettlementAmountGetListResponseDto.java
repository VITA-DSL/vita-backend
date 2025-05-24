package com.dsl.vpp.settlementAmount.dto;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;

@Builder
@Data
public class SettlementAmountGetListResponseDto {
    ArrayList<SettlementAmountGetResponseDto> settlementAmounts;
}
