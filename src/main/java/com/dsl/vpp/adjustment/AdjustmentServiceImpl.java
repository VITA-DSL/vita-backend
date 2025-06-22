package com.dsl.vpp.adjustment;

import com.dsl.vpp.weight.service.WeightService;
import com.dsl.vpp.prediction.value.PredictionInfo;
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
        final int WINDOW_SIZE = 24;
        final double DECAY_FACTOR = 0.9;
        double numerator = 0.0;
        double denominator = 0.0;

        String derId = prediction.getDerId();
        LocalDateTime timestamp = prediction.getDateTime();
        double power = prediction.getAmount();

        List<Double> trustRates = weightService.getTrustRatesByWindowSize(derId, timestamp, WINDOW_SIZE);
        double decayFactor = pow(DECAY_FACTOR, trustRates.size());
        for (Double trustRate : trustRates) { // 과거 -> 현재
            numerator += decayFactor * trustRate * power;
            denominator += decayFactor;

            decayFactor /= DECAY_FACTOR;
        }
        return numerator / denominator;
    }
}
