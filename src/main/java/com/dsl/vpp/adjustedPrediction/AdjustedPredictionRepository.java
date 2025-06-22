package com.dsl.vpp.adjustedPrediction;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AdjustedPredictionRepository extends JpaRepository<com.dsl.vpp.adjustedPrediction.AdjustedPredictionEntity, String> {
    List<AdjustedPredictionEntity> findByDerIdAndDateTimeBetween(String derId, LocalDateTime start, LocalDateTime end);
    List<AdjustedPredictionEntity> findByDerIdInAndDateTimeBetween(List<String> derIds, LocalDateTime start, LocalDateTime end);

    boolean existsByDerIdAndDateTime(String derId, LocalDateTime dateTime);
}