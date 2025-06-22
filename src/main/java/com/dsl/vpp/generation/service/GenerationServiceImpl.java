package com.dsl.vpp.generation.service;

import com.dsl.vpp.der.service.DerService;
import com.dsl.vpp.der.value.DerInfo;
import com.dsl.vpp.generation.GenerationEntity;
import com.dsl.vpp.generation.GenerationMapper;
import com.dsl.vpp.generation.GenerationRepository;
import com.dsl.vpp.generation.value.DailyGenerationInfo;
import com.dsl.vpp.generation.value.GenerationInfo;
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
    private final GenerationRepository generationRepository;

    @Override
    public String create(GenerationInfo generationInfo) {
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
    public List<GenerationInfo> readByVppIdBetween(String vppId, LocalDateTime start, LocalDateTime end) {
        List<String> derIds = derService.readByVppId(vppId).stream()
                .map(DerInfo::getId)
                .toList();

        return generationRepository.findByDerIdInAndDateTimeBetween(derIds, start, end).stream()
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
}
