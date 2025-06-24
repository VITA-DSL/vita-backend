package com.dsl.vpp.weight;

import com.dsl.vpp.generation.value.GenerationInfo;
import com.dsl.vpp.weight.value.Weight;
import com.dsl.vpp.weight.value.WeightInfo;

public class WeightMapper {
    public static WeightInfo mapToValue(WeightEntity weight) {
        return WeightInfo.builder()
                .id(weight.getId())
                .derId(weight.getDerId())
                .generation(weight.getGeneration())
                .trustRate(weight.getTrustRate())
                .dateTime(weight.getDateTime())
                .build();
    }

    public static Weight mapToWeight(WeightEntity weight) {
        return Weight.builder()
                .generation(weight.getGeneration())
                .trustRate(weight.getTrustRate())
                .build();
    }

    public static WeightEntity mapToEntity(GenerationInfo generation, Double trustRate) {
        return WeightEntity.builder()
                .derId(generation.getDerId())
                .generation(generation.getAmount())
                .trustRate(trustRate)
                .dateTime(generation.getDateTime())
                .build();
    }
}
