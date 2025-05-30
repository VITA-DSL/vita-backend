package com.dsl.vpp.adjustment;

import com.dsl.vpp.adjustedPrediction.service.AdjustedPredictionService;
import com.dsl.vpp.adjustedPrediction.value.AdjustedPredictionInfo;
import com.dsl.vpp.weight.service.WeightService;
import com.dsl.vpp.prediction.value.PredictionInfo;
import com.dsl.vpp.weight.value.WeightInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AdjustmentServiceImpl implements AdjustmentService {
    private final AdjustedPredictionService adjustedPredictionService;
    private final WeightService weightService;

    @Override
    public Double adjust(PredictionInfo predictionInfo) {
        // 조정에 필요한 준비물
        // 1. 윈도우 범위 내 대상 DER 이 수행한 예측의 신뢰도

        final int WINDOW_SIZE = 24;
        final double DECAY_FACTOR = 0.9;

        List<Double> trustRates = weightService.getLatestWeightsByWindowSize(predictionInfo.getDerId(), WINDOW_SIZE).stream()
                .map(WeightInfo::getTrustRate)
                .toList();

        double decayFactor = DECAY_FACTOR;
        double adjustedAmount = 0;
        for(int i=WINDOW_SIZE;i>=0;i--) {
            adjustedAmount += decayFactor * trustRates.get(i) * predictionInfo.getAmount();
            decayFactor *= DECAY_FACTOR;
        }

        AdjustedPredictionInfo adjustedPredictionInfo = AdjustedPredictionInfo.builder()
                .derId(predictionInfo.getDerId())
                .amount(adjustedAmount)
                .dateTime(predictionInfo.getDateTime())
                .build();

        adjustedPredictionService.create(adjustedPredictionInfo);

        return adjustedAmount;
    }

    @Override
    public Double calculateWeightedPredictionAt(String derId, Integer t) {
        return 0.0;
    }

    @Override
    public Double calculateTrustRate(Double prediction, Double observation) {
        return 0.0;
    }
}
