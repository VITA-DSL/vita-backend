package com.dsl.vpp.weight.value;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Weight {
    private Double generation;
    private Double trustRate;
}
