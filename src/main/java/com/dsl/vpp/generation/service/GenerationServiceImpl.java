package com.dsl.vpp.generation.service;

import com.dsl.vpp.adjustedPrediction.service.AdjustedPredictionService;
import com.dsl.vpp.der.service.DerService;
import com.dsl.vpp.der.value.DerInfo;
import com.dsl.vpp.generation.GenerationEntity;
import com.dsl.vpp.generation.GenerationMapper;
import com.dsl.vpp.generation.GenerationRepository;
import com.dsl.vpp.generation.value.DailyGenerationInfo;
import com.dsl.vpp.generation.value.GenerationInfo;
import com.dsl.vpp.prediction.service.PredictionService;
import com.dsl.vpp.prediction.value.PredictionInfo;
import com.dsl.vpp.weight.service.WeightService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class GenerationServiceImpl implements GenerationService {
    private final DerService derService;
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
        return generationRepository.findByDerIdAndDateTimeBetween(derId, start, end).stream()
                .map(GenerationMapper::mapToValue)
                .toList();
    }

    @Override
    public List<DailyGenerationInfo> readDailyByVppIdBetween(String vppId, LocalDate start, LocalDate end) {
        List<String> derIds = derService.readByVppId(vppId).stream()
                .map(DerInfo::getId)
                .toList();

        return generationRepository.findByDerIdInAndDateTimeBetween(derIds, start.atStartOfDay(), end.atTime(23,59,59))
                .stream()
                .collect(Collectors.groupingBy(
                        g -> g.getDateTime().toLocalDate(), // 날짜 단위 그룹화
                        Collectors.summingDouble(GenerationEntity::getAmount) // 날짜 단위 발전량 합계
                ))
                .entrySet()
                .stream()
                .map(GenerationMapper::mapToValue)
                .toList();
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
