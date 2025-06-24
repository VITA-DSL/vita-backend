package com.dsl.vpp.adjustment;

import com.dsl.vpp.weight.service.WeightService;
import com.dsl.vpp.prediction.value.PredictionInfo;
import com.dsl.vpp.weight.value.Weight;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static java.lang.Math.min;
import static java.lang.Math.pow;

@RequiredArgsConstructor
@Service
public class AdjustmentServiceImpl implements AdjustmentService {
    private final WeightService weightService;

    @Override
    public Double adjust(PredictionInfo prediction) {
        final int WINDOW_SIZE = 72;
        final double DECAY_FACTOR = 0.9;
        double numerator = 0.0;
        double denominator = 0.0;

        String derId = prediction.getDerId();
        LocalDateTime timestamp = prediction.getDateTime();
        double power = prediction.getAmount();

        List<Weight> weights = weightService.getWeightsByWindowSize(derId, timestamp, WINDOW_SIZE);
        double decayFactor = 1.0;
        for (Weight weight : weights) { // weight = 최근 -> 과거
            numerator += decayFactor * min(weight.getTrustRate(), 1.5) * weight.getPrediction();
            denominator += decayFactor;

            decayFactor *= DECAY_FACTOR;
        }
        if(denominator == 0) {
            return power;
        }
        return numerator / denominator;
    }
}
