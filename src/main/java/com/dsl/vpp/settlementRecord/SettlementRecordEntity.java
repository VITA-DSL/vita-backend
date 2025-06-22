package com.dsl.vpp.settlementRecord;

import com.dsl.vpp.settlementRecord.value.SettlementAmountInfo;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity(name = "settlementAmount")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class SettlementRecordEntity {
    @Id
    String id;
    @Embedded
    SettlementAmountInfo adjusted;
    @Embedded
    SettlementAmountInfo original;
    LocalDateTime dateTime;

    @PrePersist
    public void assignId() {
        if (this.id == null) {
            this.id = UUID.randomUUID().toString();
        }
    }
}
