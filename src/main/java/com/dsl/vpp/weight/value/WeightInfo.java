package com.dsl.vpp.weight.value;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
public class WeightInfo {
    private String id;
    private String derId;
    private Double trustRate;
    private LocalDateTime dateTime;
}
