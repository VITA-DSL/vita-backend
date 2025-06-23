package com.dsl.vpp.settlementRecord;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SettlementRecordRepository extends JpaRepository<SettlementRecordEntity, String> {
    List<SettlementRecordEntity> findByDerIdAndDateTimeBetween(String derId, LocalDateTime start, LocalDateTime end);
    List<SettlementRecordEntity> findByDerIdInAndDateTimeBetween(List<String> derId, LocalDateTime start, LocalDateTime end);
}
