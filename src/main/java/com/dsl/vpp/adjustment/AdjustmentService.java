package com.dsl.vpp.adjustment;

import com.dsl.vpp.prediction.value.PredictionInfo;

public interface AdjustmentService {
    String adjust(PredictionInfo predictionInfo);
}
