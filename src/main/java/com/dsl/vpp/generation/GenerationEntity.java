package com.dsl.vpp.generation;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@Entity(name = "generation")
public class GenerationEntity {
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
