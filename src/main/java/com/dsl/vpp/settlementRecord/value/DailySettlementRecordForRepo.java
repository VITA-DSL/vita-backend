package com.dsl.vpp.settlementRecord.value;

import java.time.LocalDate;

public interface DailySettlementRecordForRepo {
    Integer getOriginalTotalAmount();
    Integer getAdjustedTotalAmount();
    LocalDate getDate();
}
