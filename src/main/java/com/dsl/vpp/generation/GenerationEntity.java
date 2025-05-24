package com.dsl.vpp.generation;

import com.dsl.vpp.der.DerEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity(name = "generation")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class GenerationEntity {
    @Id
    String id;
    String derId;
    Double amount;
    LocalDateTime dateTime;

    @PrePersist
    public void assignId() {
        if (this.id == null) {
            this.id = UUID.randomUUID().toString();
        }
    }
}
