package com.dsl.vpp.weight;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WeightRepository extends JpaRepository<WeightEntity, String> {
    @Query(value = "SELECT * FROM weight WHERE der_id = :derId ORDER BY date_time DESC LIMIT :windowSize", nativeQuery = true)
    List<WeightEntity> findLatestWeightsInWindowByDerId(@Param("windowSize") Integer windowSize, @Param("derId") String derId);
}
