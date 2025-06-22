package com.dsl.vpp.weight;

import com.dsl.vpp.generation.value.GenerationInfo;
import com.dsl.vpp.weight.value.WeightInfo;

public class WeightMapper {
    public static WeightInfo mapToValue(WeightEntity weight) {
        return WeightInfo.builder()
                .id(weight.getId())
                .derId(weight.getDerId())
                .trustRate(weight.getTrustRate())
                .dateTime(weight.getDateTime())
                .build();
    }

    public static WeightEntity mapToEntity(GenerationInfo generation, Double trustRate) {
        return WeightEntity.builder()
                .derId(generation.getDerId())
                .trustRate(trustRate)
                .dateTime(generation.getDateTime())
                .build();
    }
}
