package com.dsl.vpp.prediction.service;

import com.dsl.vpp.der.service.DerService;
import com.dsl.vpp.prediction.PredictionEntity;
import com.dsl.vpp.prediction.PredictionMapper;
import com.dsl.vpp.prediction.PredictionRepository;
import com.dsl.vpp.prediction.value.PredictionInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PredictionServiceImpl implements PredictionService {
    private final PredictionRepository predictionRepository;
    private final DerService derService;

    @Override
    public String create(PredictionInfo predictionInfo) {
        derService.validateIdExists(predictionInfo.getDerId());

        PredictionEntity predictionEntity = PredictionMapper.mapToEntity(predictionInfo);
        return predictionRepository.save(predictionEntity).getId();
    }

    @Override
    public List<PredictionInfo> readByDerBetween(String derId, LocalDateTime start, LocalDateTime end) {
        List<PredictionEntity> predictionEntityList = predictionRepository.findByDerIdAndDateTimeBetween(derId, start, end);
        return PredictionMapper.mapToValue(predictionEntityList);
    }
}
