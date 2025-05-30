package com.dsl.vpp.weight;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity(name="weight")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class WeightEntity {
    @Id
    private String id;
    private String derId;
    private Double trustRate;
    private LocalDateTime dateTime;
}
