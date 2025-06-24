package com.dsl.vpp.weight;

import com.dsl.vpp.generation.value.GenerationInfo;
import com.dsl.vpp.prediction.value.PredictionInfo;
import com.dsl.vpp.weight.value.Weight;
import com.dsl.vpp.weight.value.WeightInfo;

public class WeightMapper {
    public static WeightInfo mapToValue(WeightEntity weight) {
        return WeightInfo.builder()
                .id(weight.getId())
                .derId(weight.getDerId())
                .prediction(weight.getPrediction())
                .trustRate(weight.getTrustRate())
                .dateTime(weight.getDateTime())
                .build();
    }

    public static Weight mapToWeight(WeightEntity weight) {
        return Weight.builder()
                .prediction(weight.getPrediction())
                .trustRate(weight.getTrustRate())
                .build();
    }

    public static WeightEntity mapToEntity(PredictionInfo prediction, Double trustRate) {
        return WeightEntity.builder()
                .derId(prediction.getDerId())
                .prediction(prediction.getAmount())
                .trustRate(trustRate)
                .dateTime(prediction.getDateTime())
                .build();
    }
}
