package com.dsl.vpp.settlement.service;

import java.time.LocalDateTime;

public interface SettlementService {
    String settle(String generationId);
    void settleByVppIdBetween(String vppId, LocalDateTime start, LocalDateTime end);
    Double calculateUnitPrice(Double observation, Double prediction);
}
