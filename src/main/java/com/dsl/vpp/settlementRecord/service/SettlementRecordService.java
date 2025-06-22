package com.dsl.vpp.settlementRecord.service;

import com.dsl.vpp.settlementRecord.value.DailySettlementRecordInfo;
import com.dsl.vpp.settlementRecord.value.SettlementRecordInfo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface SettlementRecordService {
    String create(SettlementRecordInfo settlementRecordInfo);
    SettlementRecordInfo readById(String id);
    List<SettlementRecordInfo> readByDerIdBetween(String derId, LocalDateTime start, LocalDateTime end);
    List<SettlementRecordInfo> readByVppIdBetween(String vppId, LocalDateTime start, LocalDateTime end);
    List<DailySettlementRecordInfo> readDailyByVppIdBetween(String vppId, LocalDate start, LocalDate end);
}
