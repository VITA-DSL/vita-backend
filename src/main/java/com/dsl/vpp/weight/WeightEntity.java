package com.dsl.vpp.weight;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity(name="weight")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class WeightEntity {
    @Id
    private String id;
    private String derId;
    private Double prediction;
    private Double trustRate;
    private LocalDateTime dateTime;

    @PrePersist
    public void assignId() {
        if (this.id == null) {
            this.id = UUID.randomUUID().toString();
        }
    }
}
