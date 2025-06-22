package com.dsl.vpp.settlementRecord.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class DailySettlementRecordGetListResponseDto {
    List<DailySettlementRecordGetResponseDto> settlements;
}
