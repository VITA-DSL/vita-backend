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
import java.util.stream.Collectors;

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
    public AdjustedPredictionInfo readByPredictionId(String predictionId) {
        AdjustedPredictionEntity adjustedPredictionEntity = adjustedPredictionRepository.findByPredictionId(predictionId)
                .orElseThrow(()->new IllegalArgumentException("존재하지 않는 예측입니다."));
        return AdjustedPredictionMapper.mapToValue(adjustedPredictionEntity);
    }


    @Override
    public List<AdjustedPredictionInfo> readByDerIdBetween(String derId, LocalDateTime start, LocalDateTime end) {
        List<PredictionInfo> predictionInfoList = predictionService.readByDerIdBetween(derId, start, end);
        return predictionInfoList.stream()
                .map(predictionInfo -> readByPredictionId(predictionInfo.getId())) // null 처리 필요
                .collect(Collectors.toList());
    }

    @Override
    public List<AdjustedPredictionInfo> readByVppIdBetween(String vppId, LocalDateTime start, LocalDateTime end) {
        return predictionService.readByVppIdBetween(vppId, start, end).stream()
                .map(predictionInfo -> readByPredictionId(predictionInfo.getId()))
                .collect(Collectors.toList());
    }
}
