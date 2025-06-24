package com.dsl.vpp.settlementRecord.value;

import java.time.LocalDate;

public interface DailySettlementRecordForRepo {
    Long getTotalOriginalAmount();
    Long getTotalAdjustedAmount();
    LocalDate getDate();
}
