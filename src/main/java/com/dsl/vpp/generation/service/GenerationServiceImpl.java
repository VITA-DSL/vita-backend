package com.dsl.vpp.generation.service;

import com.dsl.vpp.generation.GenerationMapper;
import com.dsl.vpp.generation.GenerationRepository;
import com.dsl.vpp.generation.value.GenerationInfo;
import com.dsl.vpp.prediction.service.PredictionService;
import com.dsl.vpp.prediction.value.PredictionInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Service
public class GenerationServiceImpl implements GenerationService {
    private final PredictionService predictionService;
    private final GenerationRepository generationRepository;

    @Override
    public String create(GenerationInfo generation) {
        predictionService.validateIdExists(generation.getPredictionId());
        return generationRepository.save(GenerationMapper.mapToEntity(generation)).getId();
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

    private List<GenerationInfo> readByPredictions(List<PredictionInfo> predictions) {
        List<String> predictionIdList = predictions.stream()
                .map(PredictionInfo::getId)
                .toList();
        return generationRepository.findByPredictionIdIn(predictionIdList).stream()
                .map(GenerationMapper::mapToValue)
                .toList();
    }
}
