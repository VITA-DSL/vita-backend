package com.dsl.vpp.generation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface GenerationRepository extends JpaRepository<GenerationEntity, String> {
    List<GenerationEntity> findByDerIdAndDateTimeBetween(String derId, LocalDateTime start, LocalDateTime end);
}
