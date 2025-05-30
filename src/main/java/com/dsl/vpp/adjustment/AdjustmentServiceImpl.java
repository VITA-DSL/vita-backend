package com.dsl.vpp.adjustment;

import com.dsl.vpp.adjustedPrediction.AdjustedPredictionMapper;
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
    public String adjust(PredictionInfo predictionInfo) {
        final int WINDOW_SIZE = 24;
        final double DECAY_FACTOR = 0.9;

        List<Double> trustRates = weightService.getLatestWeightsByWindowSize(predictionInfo.getDerId(), WINDOW_SIZE).stream()
                .map(WeightInfo::getTrustRate)
                .toList();

        double decayFactor = DECAY_FACTOR;
        double adjustedAmount = 0;
        for(int i=WINDOW_SIZE;i>=0;i--) {
            Double trustRate = trustRates.get(i);
            if(trustRate == null) {

            }
            adjustedAmount += decayFactor * trustRates.get(i) * predictionInfo.getAmount();
            decayFactor *= DECAY_FACTOR;
        }

        AdjustedPredictionInfo adjustedPredictionInfo = AdjustedPredictionMapper.mapToValue(predictionInfo, adjustedAmount);
        return adjustedPredictionService.create(adjustedPredictionInfo);
    }
}
