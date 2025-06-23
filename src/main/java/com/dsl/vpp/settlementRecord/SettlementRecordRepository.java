package com.dsl.vpp.settlementRecord;

import com.dsl.vpp.settlementRecord.value.DailySettlementRecordForRepo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SettlementRecordRepository extends JpaRepository<SettlementRecordEntity, String> {
    List<SettlementRecordEntity> findByDerIdAndDateTimeBetween(String derId, LocalDateTime start, LocalDateTime end);
    List<SettlementRecordEntity> findByDerIdInAndDateTimeBetween(List<String> derId, LocalDateTime start, LocalDateTime end);

    @Query(value = """
            SELECT SUM(adjusted_amount) AS totalAdjustedAmount, SUM(original_amount) AS totalOriginalAmount, DATE(date_time) AS date
            FROM settlement_amount
            WHERE der_id IN (:derIds)
            AND date_time BETWEEN (:start) AND (:end)
            GROUP BY DATE(date_time)
            ORDER BY DATE(date_time)
            """,
            nativeQuery = true)
    List<DailySettlementRecordForRepo> findDailyByDerIdInAndDateTimeBetween(@Param("derIds") List<String> derIds, @Param("start") LocalDateTime start, @Param("end") LocalDateTime end);
}
