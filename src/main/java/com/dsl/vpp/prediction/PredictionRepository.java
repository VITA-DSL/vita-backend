package com.dsl.vpp.prediction;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PredictionRepository extends JpaRepository<PredictionEntity, String> {
    List<PredictionEntity> findByDerIdAndDateTimeBetween(String derId, LocalDateTime start, LocalDateTime end);
    List<PredictionEntity> findByDerIdInAndDateTimeBetween(List<String> derIds, LocalDateTime start, LocalDateTime end);

    @Query(value = "SELECT * FROM prediction WHERE MONTH(date_time) = :month", nativeQuery = true)
    List<PredictionEntity> findByMonth(@Param("month") int month);
}
