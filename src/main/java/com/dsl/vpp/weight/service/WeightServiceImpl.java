package com.dsl.vpp.weight.service;

import com.dsl.vpp.generation.service.GenerationService;
import com.dsl.vpp.generation.value.GenerationInfo;
import com.dsl.vpp.prediction.service.PredictionService;
import com.dsl.vpp.prediction.value.PredictionInfo;
import com.dsl.vpp.weight.WeightEntity;
import com.dsl.vpp.weight.WeightMapper;
import com.dsl.vpp.weight.WeightRepository;
import com.dsl.vpp.weight.value.Weight;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.lang.Math.abs;
import static java.lang.Math.min;

@RequiredArgsConstructor
@Service
public class WeightServiceImpl implements WeightService {
    private final PredictionService predictionService;
    private final GenerationService generationService;
    private final WeightRepository weightRepository;

    @Transactional
    @Override
    public void generateWeight(String generationId) {
        GenerationInfo generation = generationService.readById(generationId);
        PredictionInfo prediction = predictionService.readById(generation.getPredictionId());

        Double trustRate = calculateTrustRate(prediction.getAmount(), generation.getAmount());

        WeightEntity weightEntity = WeightMapper.mapToEntity(prediction, trustRate);
        weightRepository.save(weightEntity);
    }

    @Transactional
    @Override
    public void generateWeightsByVppIdBetween(String vppId, LocalDateTime start, LocalDateTime end) {
        List<GenerationInfo> generations = generationService.readByVppIdBetween(vppId, start, end);
        List<String> predictionIds = generations.stream()
                .map(GenerationInfo::getPredictionId)
                .distinct()
                .toList();

        List<PredictionInfo> predictions = predictionService.readByIds(predictionIds);

        Map<String, PredictionInfo> predictionMap = predictions.stream()
                .collect(Collectors.toMap(PredictionInfo::getId, p -> p));

        for (GenerationInfo generation : generations) {
            PredictionInfo prediction = predictionMap.get(generation.getPredictionId());

            if (prediction != null) {
                double trustRate = calculateTrustRate(prediction.getAmount(), generation.getAmount());
                WeightEntity weight = WeightMapper.mapToEntity(prediction, trustRate);
                weightRepository.save(weight);
            }
        }
    }

    @Override
    public List<Weight> getWeightsByWindowSize(String derId, LocalDateTime timestamp, Integer windowSize) {
        return weightRepository.findWeightsBeforeDateTime(derId, timestamp, windowSize).stream()
                .map(WeightMapper::mapToWeight)
                .toList();
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
}
