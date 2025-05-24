package com.dsl.vpp.adjustedPrediction.service;

import com.dsl.vpp.adjustedPrediction.AdjustedPredictionEntity;
import com.dsl.vpp.adjustedPrediction.AdjustedPredictionMapper;
import com.dsl.vpp.adjustedPrediction.AdjustedPredictionRepository;
import com.dsl.vpp.adjustedPrediction.value.AdjustedPredictionInfo;
import com.dsl.vpp.der.service.DerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AdjustedPredictionServiceImpl implements AdjustedPredictionService {
    private final DerService derService;
    private final AdjustedPredictionRepository adjustedPredictionRepository;

    @Override
    public String create(AdjustedPredictionInfo adjustedPredictionInfo) {
        derService.validateIdExists(adjustedPredictionInfo.getDerId());
        AdjustedPredictionEntity adjustedPredictionEntity = AdjustedPredictionMapper.mapToEntity(adjustedPredictionInfo);
        return adjustedPredictionRepository.save(adjustedPredictionEntity).getId();
    }

    @Override
    public List<AdjustedPredictionInfo> readByDerBetween(String derId, LocalDateTime start, LocalDateTime end) {
        List<AdjustedPredictionEntity> adjustedPredictionEntityList = adjustedPredictionRepository.findByDerIdAndDateTimeBetween(derId,start,end);
        return AdjustedPredictionMapper.mapToValue(adjustedPredictionEntityList);
    }

    @Override
    public List<AdjustedPredictionInfo> readByVppBetween(String vppId, LocalDateTime start, LocalDateTime end) {
        return derService.readByVppId(vppId).stream()
                .flatMap(derInfo -> readByDerBetween(derInfo.getId(), start, end).stream())
                .collect(Collectors.toList());
    }
}
