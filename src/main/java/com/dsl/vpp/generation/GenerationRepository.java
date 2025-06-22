package com.dsl.vpp.generation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface GenerationRepository extends JpaRepository<GenerationEntity, String> {
    Optional<GenerationEntity> findByPredictionId(String predictionId);
    List<GenerationEntity> findByDerIdAndDateTimeBetween(String derId, LocalDateTime start, LocalDateTime end);
    List<GenerationEntity> findByDerIdInAndDateTimeBetween(List<String> derIds, LocalDateTime start, LocalDateTime end);
}
