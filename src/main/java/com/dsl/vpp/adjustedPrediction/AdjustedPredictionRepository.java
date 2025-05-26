package com.dsl.vpp.adjustedPrediction;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdjustedPredictionRepository extends JpaRepository<com.dsl.vpp.adjustedPrediction.AdjustedPredictionEntity, String> {
    Optional<AdjustedPredictionEntity> findByPredictionId(String predictionId);
}