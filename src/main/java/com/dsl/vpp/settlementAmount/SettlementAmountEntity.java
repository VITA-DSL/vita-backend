package com.dsl.vpp.settlementAmount;

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
public class SettlementAmountEntity {
    @Id
    String id;
    String generationId;
    Double unitPrice;
    Integer amount;
    LocalDateTime settledAt;

    @PrePersist
    public void assignId() {
        if (this.id == null) {
            this.id = UUID.randomUUID().toString();
        }
    }
}
