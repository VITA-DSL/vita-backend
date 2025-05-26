package com.dsl.vpp.adjustedPrediction.service;

import com.dsl.vpp.adjustedPrediction.AdjustedPredictionEntity;
import com.dsl.vpp.adjustedPrediction.AdjustedPredictionMapper;
import com.dsl.vpp.adjustedPrediction.AdjustedPredictionRepository;
import com.dsl.vpp.adjustedPrediction.value.AdjustedPredictionInfo;
import com.dsl.vpp.prediction.service.PredictionService;
import com.dsl.vpp.prediction.value.PredictionInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Service
public class AdjustedPredictionServiceImpl implements AdjustedPredictionService {
    private final PredictionService predictionService;
    private final AdjustedPredictionRepository adjustedPredictionRepository;

    @Override
    public String create(AdjustedPredictionInfo adjustedPredictionInfo) {
        predictionService.validateIdExists(adjustedPredictionInfo.getPredictionId());
        AdjustedPredictionEntity adjustedPredictionEntity = AdjustedPredictionMapper.mapToEntity(adjustedPredictionInfo);
        return adjustedPredictionRepository.save(adjustedPredictionEntity).getId();
    }

    @Override
    public AdjustedPredictionInfo readById(String id) {
        return adjustedPredictionRepository.findById(id)
                .map(AdjustedPredictionMapper::mapToValue)
                .orElseThrow(()->new NoSuchElementException("존재하지 않는 보정 데이터입니다."));
    }

    @Override
    public AdjustedPredictionInfo readByPredictionId(String predictionId) {
        return adjustedPredictionRepository.findByPredictionId(predictionId)
                .map(AdjustedPredictionMapper::mapToValue)
                .orElseThrow(()->new NoSuchElementException("해당 예측은 아직 보정이 이루어지지 않았습니다."));
    }

    @Override
    public List<AdjustedPredictionInfo> readByDerIdBetween(String derId, LocalDateTime start, LocalDateTime end) {
        List<PredictionInfo> predictions = predictionService.readByDerIdBetween(derId, start, end);
        return readByPredictions(predictions);
    }

    @Override
    public List<AdjustedPredictionInfo> readByVppIdBetween(String vppId, LocalDateTime start, LocalDateTime end) {
        List<PredictionInfo> predictions = predictionService.readByVppIdBetween(vppId, start, end);
        return readByPredictions(predictions);
    }

    private List<AdjustedPredictionInfo> readByPredictions(List<PredictionInfo> predictions) {
        List<String> predictionIds = predictions.stream()
                .map(PredictionInfo::getId)
                .toList();

        return adjustedPredictionRepository.findByPredictionIdIn(predictionIds).stream()
                .map(AdjustedPredictionMapper::mapToValue)
                .toList();
    }
}
