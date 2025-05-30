package com.dsl.vpp.generation.service;

import com.dsl.vpp.adjustedPrediction.service.AdjustedPredictionService;
import com.dsl.vpp.generation.GenerationEntity;
import com.dsl.vpp.generation.GenerationMapper;
import com.dsl.vpp.generation.GenerationRepository;
import com.dsl.vpp.generation.value.GenerationInfo;
import com.dsl.vpp.prediction.service.PredictionService;
import com.dsl.vpp.prediction.value.PredictionInfo;
import com.dsl.vpp.weight.service.WeightService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Service
public class GenerationServiceImpl implements GenerationService {
    private final PredictionService predictionService;
    private final AdjustedPredictionService adjustedPredictionService;
    private final WeightService weightService;
    private final GenerationRepository generationRepository;

    @Override
    public String create(GenerationInfo generationInfo) {
        PredictionInfo predictionInfo = predictionService.readById(generationInfo.getPredictionId());

        weightService.updateWeight(predictionInfo, generationInfo);

        GenerationEntity generationEntity = GenerationMapper.mapToEntity(generationInfo);
        return generationRepository.save(generationEntity).getId();
    }

    @Override
    public GenerationInfo readById(String id) {
        return generationRepository.findById(id)
                .map(GenerationMapper::mapToValue)
                .orElseThrow(()-> new NoSuchElementException("존재하지 않는 전력 데이터입니다."));
    }

    @Override
    public GenerationInfo readByPredictionId(String predictionId) {
        return generationRepository.findByPredictionId(predictionId)
                .map(GenerationMapper::mapToValue)
                .orElseThrow(()-> new NoSuchElementException("해당 예측은 아직 전력 생산이 이루어지지 않았습니다."));
    }

    @Override
    public List<GenerationInfo> readByDerIdBetween(String derId, LocalDateTime start, LocalDateTime end) {
        List<PredictionInfo> predictions = predictionService.readByDerIdBetween(derId, start, end);
        return readByPredictions(predictions);
    }

    @Override
    public List<GenerationInfo> readByVppIdBetween(String vppId, LocalDateTime start, LocalDateTime end) {
        List<PredictionInfo> predictions = predictionService.readByVppIdBetween(vppId, start, end);
        return readByPredictions(predictions);
    }

    @Override
    public Double calculateOriginalErrorRate(String generationId) {
        GenerationInfo generationInfo = this.readById(generationId);
        Double generatedAmount = generationInfo.getAmount();
        Double predictedAmount = predictionService.readById(generationInfo.getPredictionId()).getAmount();
        return calculateErrorRate(generatedAmount, predictedAmount);
    }

    @Override
    public Double calculateAdjustedErrorRate(String generationId) {
        GenerationInfo generationInfo = this.readById(generationId);
        Double generatedAmount = generationInfo.getAmount();
        Double predictedAmount = adjustedPredictionService.readById(generationInfo.getPredictionId()).getAmount();
        return calculateErrorRate(generatedAmount, predictedAmount);
    }

    private Double calculateErrorRate(Double generatedAmount, Double predictedAmount) {
        return ((generatedAmount - predictedAmount) / generatedAmount) * 100.0;
    }

    private List<GenerationInfo> readByPredictions(List<PredictionInfo> predictions) {
        List<String> predictionIdList = predictions.stream()
                .map(PredictionInfo::getId)
                .toList();
        return generationRepository.findByPredictionIdIn(predictionIdList).stream()
                .map(GenerationMapper::mapToValue)
                .toList();
    }
}
