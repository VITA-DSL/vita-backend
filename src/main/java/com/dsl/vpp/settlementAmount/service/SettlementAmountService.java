package com.dsl.vpp.settlementAmount.service;

import com.dsl.vpp.settlementAmount.value.SettlementAmountInfo;

import java.time.LocalDateTime;
import java.util.List;

public interface SettlementAmountService {
    String create(SettlementAmountInfo settlementAmountInfo);
    SettlementAmountInfo readById(String id);
    SettlementAmountInfo readByGenerationId(String generationId);
    List<SettlementAmountInfo> readByDerIdBetween(String derId, LocalDateTime start, LocalDateTime end);
    List<SettlementAmountInfo> readByVppIdBetween(String vppId, LocalDateTime start, LocalDateTime end);
}
