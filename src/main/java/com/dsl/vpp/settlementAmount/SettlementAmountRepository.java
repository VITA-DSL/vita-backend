package com.dsl.vpp.settlementAmount;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SettlementAmountRepository extends JpaRepository<SettlementAmountEntity, String> {
    Optional<SettlementAmountEntity> findByGenerationId(String generationId);
}
