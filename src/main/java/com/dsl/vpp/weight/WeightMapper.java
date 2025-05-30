package com.dsl.vpp.weight;

import com.dsl.vpp.weight.value.WeightInfo;

public class WeightMapper {
    public static WeightInfo mapToValue(WeightEntity weight) {
        return WeightInfo.builder()
                .trustRate(weight.getTrustRate())
                .build();
    }
}
