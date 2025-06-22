package com.dsl.vpp.weight;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface WeightRepository extends JpaRepository<WeightEntity, String> {
    @Query(value = "SELECT * FROM weight WHERE der_id = :derId AND date_time < :dateTime ORDER BY date_time DESC LIMIT :windowSize", nativeQuery = true)
    List<WeightEntity> findWeightsBeforeDateTime(@Param("derId") String derId, @Param("dateTime") LocalDateTime dateTime, @Param("windowSize") Integer windowSize);
}
