package com.dsl.vpp.generation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GenerationRepository extends JpaRepository<GenerationEntity, String> {
    Optional<GenerationEntity> findByPredictionId(String predictionId);
    List<GenerationEntity> findByPredictionIdIn(List<String> predictionId);
}
