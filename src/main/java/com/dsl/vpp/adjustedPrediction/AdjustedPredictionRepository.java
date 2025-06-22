package com.dsl.vpp.adjustedPrediction;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AdjustedPredictionRepository extends JpaRepository<com.dsl.vpp.adjustedPrediction.AdjustedPredictionEntity, String> {
    List<AdjustedPredictionEntity> findByDerIdAndDateTimeBetween(String derId, LocalDateTime start, LocalDateTime end);
    List<AdjustedPredictionEntity> findByDerIdInAndDateTimeBetween(List<String> derIds, LocalDateTime start, LocalDateTime end);
    @Modifying
    @Query(value = """
    INSERT IGNORE INTO adjusted (id, der_id, amount, date_time)
    VALUES (:id, :derId, :amount, :dateTime)
    """, nativeQuery = true)
    @Transactional
    void insertIgnore(@Param("id") String id, @Param("derId") String derId, @Param("amount") double amount, @Param("dateTime") LocalDateTime dateTime);
}