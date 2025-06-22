package com.dsl.vpp.settlementRecord.dto;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;

@Builder
@Data
public class SettlementGetListResponseDto {
    ArrayList<SettlementGetResponseDto> settlementAmounts;
}
