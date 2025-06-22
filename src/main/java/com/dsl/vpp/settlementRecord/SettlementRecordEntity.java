package com.dsl.vpp.settlementRecord;

import com.dsl.vpp.settlementRecord.value.SettlementAmountInfo;
import jakarta.persistence.*;
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
    String derId;
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "unitPrice", column = @Column(name = "adjusted_unit_price")),
            @AttributeOverride(name = "power", column = @Column(name = "adjusted_power")),
            @AttributeOverride(name = "amount", column = @Column(name = "adjusted_amount"))
    })
    SettlementAmountInfo adjusted;
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "unitPrice", column = @Column(name = "original_unit_price")),
            @AttributeOverride(name = "power", column = @Column(name = "original_power")),
            @AttributeOverride(name = "amount", column = @Column(name = "original_amount"))
    })
    SettlementAmountInfo original;
    LocalDateTime dateTime;

    @PrePersist
    public void assignId() {
        if (this.id == null) {
            this.id = UUID.randomUUID().toString();
        }
    }
}
