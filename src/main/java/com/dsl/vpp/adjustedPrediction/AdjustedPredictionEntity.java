package com.dsl.vpp.adjustedPrediction;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity(name = "adjusted")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class AdjustedPredictionEntity {
    @Id
    String id;
    String derId;
    String predictionId;
    Double amount;
    LocalDateTime dateTime;

    @PrePersist
    public void assignId() {
        if (this.id == null) {
            this.id = UUID.randomUUID().toString();
        }
    }
}

