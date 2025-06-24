package com.dsl.vpp.settlementRecord.value;

import java.time.LocalDate;

public interface DailySettlementRecordForRepo {
    Integer getTotalOriginalAmount();
    Integer getTotalAdjustedAmount();
    LocalDate getDate();
}
