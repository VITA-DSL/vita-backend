package com.dsl.vpp.prediction;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PredictionRepository extends JpaRepository<PredictionEntity, String> {
    List<PredictionEntity> findByDerIdAndDateTimeBetween(String derId, LocalDateTime start, LocalDateTime end);
    List<PredictionEntity> findByDateTimeBetween(LocalDateTime start, LocalDateTime end);
}
