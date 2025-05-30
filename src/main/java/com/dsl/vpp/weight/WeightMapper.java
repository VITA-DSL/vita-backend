package com.dsl.vpp.weight;

import com.dsl.vpp.prediction.value.PredictionInfo;
import com.dsl.vpp.weight.value.WeightInfo;

public class WeightMapper {
    public static WeightInfo mapToValue(WeightEntity weight) {
        return WeightInfo.builder()
                .trustRate(weight.getTrustRate())
                .build();
    }

    public static WeightEntity mapToEntity(WeightInfo weightInfo) {
        return WeightEntity.builder()
                .derId(weightInfo.getDerId())
                .trustRate(weightInfo.getTrustRate())
                .dateTime(weightInfo.getDateTime())
                .build();
    }

    public static WeightEntity mapToEntity(PredictionInfo predictionInfo, Double trustRate) {
        return WeightEntity.builder()
                .derId(predictionInfo.getDerId())
                .trustRate(trustRate)
                .dateTime(predictionInfo.getDateTime())
                .build();
    }
}
