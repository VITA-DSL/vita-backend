package com.dsl.vpp.weight.service;

import com.dsl.vpp.weight.WeightMapper;
import com.dsl.vpp.weight.WeightRepository;
import com.dsl.vpp.weight.value.WeightInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class WeightServiceImpl implements WeightService {
    private final WeightRepository weightRepository;

    @Override
    public List<WeightInfo> getLatestWeightsByWindowSize(String derId, Integer windowSize) {
        return weightRepository.findLatestWeightsInWindowByDerId(windowSize, derId).stream()
                .map(WeightMapper::mapToValue)
                .toList();
    }
}
