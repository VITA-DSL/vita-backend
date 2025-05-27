package com.dsl.vpp.settlement.service;

public interface SettlementService {
    String settle(String generationId);
    Double calculateUnitPrice(Double generationAmount, Double errorRate);
}
