package com.dsl.vpp.generation;

import com.dsl.vpp.generation.value.DailyGenerationForRepo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface GenerationRepository extends JpaRepository<GenerationEntity, String> {
    Optional<GenerationEntity> findByPredictionId(String predictionId);
    List<GenerationEntity> findByDerIdAndDateTimeBetween(String derId, LocalDateTime start, LocalDateTime end);
    List<GenerationEntity> findByDerIdInAndDateTimeBetween(List<String> derIds, LocalDateTime start, LocalDateTime end);

    @Query(value = """
            SELECT SUM(amount) AS totalAmount, DATE(date_time) AS date
            FROM generation
            WHERE der_id IN (:derIds)
            AND date_time BETWEEN (:start) AND (:end)
            GROUP BY DATE(date_time)
            ORDER BY DATE(date_time)
            """,
            nativeQuery = true)
    List<DailyGenerationForRepo> findDailyByDerIdInAndDateTimeBetween(@Param("derIds") List<String> derIds, @Param("start") LocalDateTime start, @Param("end") LocalDateTime end);
}
