package com.dsl.vpp.settlementRecord;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SettlementRecordRepository extends JpaRepository<SettlementRecordEntity, String> {
    Optional<SettlementRecordEntity> findByGenerationId(String generationId);
    List<SettlementRecordEntity> findByGenerationIdIn(List<String> generationId);
}
