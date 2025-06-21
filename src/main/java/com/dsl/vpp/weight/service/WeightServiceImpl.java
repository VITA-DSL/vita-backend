package com.dsl.vpp.weight.service;

import com.dsl.vpp.generation.value.GenerationInfo;
import com.dsl.vpp.prediction.value.PredictionInfo;
import com.dsl.vpp.weight.WeightEntity;
import com.dsl.vpp.weight.WeightMapper;
import com.dsl.vpp.weight.WeightRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static java.lang.Math.abs;
import static java.lang.Math.min;

@RequiredArgsConstructor
@Service
public class WeightServiceImpl implements WeightService {
    private final WeightRepository weightRepository;

    @Override
    public void updateWeight(PredictionInfo predictionInfo, GenerationInfo generationInfo) {
        Double trustRate = calculateTrustRate(predictionInfo.getAmount(), generationInfo.getAmount());
        WeightEntity weightEntity = WeightMapper.mapToEntity(predictionInfo, trustRate);
        weightRepository.save(weightEntity);
    }

    @Override
    public Double calculateTrustRate(Double prediction, Double observation) {
        if (observation >= prediction) {
            return 1.0/(1.0 - min(calculateAbsoluteRelativeError(prediction, observation), 1.0));
        } else {
            return 1.0 - min(calculateAbsoluteRelativeError(prediction, observation), 1.0);
        }
    }

    private Double calculateAbsoluteRelativeError(Double prediction, Double observation) {
        return abs((prediction - observation) / observation);
    }

    @Override
    public List<Double> getTrustRatesByWindowSize(String derId, LocalDateTime timestamp, Integer windowSize) {
        return weightRepository.findWeightsBeforeDateTime(derId, timestamp, windowSize).stream()
                .map(WeightEntity::getTrustRate)
                .toList();
    }
}
