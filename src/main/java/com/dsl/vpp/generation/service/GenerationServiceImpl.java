package com.dsl.vpp.generation.service;

import com.dsl.vpp.der.service.DerService;
import com.dsl.vpp.generation.GenerationEntity;
import com.dsl.vpp.generation.GenerationMapper;
import com.dsl.vpp.generation.GenerationRepository;
import com.dsl.vpp.generation.value.GenerationInfo;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GenerationServiceImpl implements GenerationService {
    DerService derService;
    GenerationRepository generationRepository;

    public GenerationServiceImpl(DerService derService, GenerationRepository generationRepository) {
        this.derService = derService;
        this.generationRepository = generationRepository;
    }

    @Override
    public String create(GenerationInfo generation) {
        derService.validateIdExists(generation.getDerId());
        return generationRepository.save(GenerationMapper.mapToEntity(generation)).getId();
    }

    @Override
    public List<GenerationInfo> readByDerBetween(String derId, LocalDateTime start, LocalDateTime end) {
        List<GenerationEntity> generationEntityList = generationRepository.findByDerIdAndDateTimeBetween(derId,start,end);
        return GenerationMapper.mapToValue(generationEntityList);
    }

    @Override
    public List<GenerationInfo> readByVppBetween(String vppId, LocalDateTime start, LocalDateTime end) {
        return derService.readByVppId(vppId).stream()
                .flatMap(derInfo -> readByDerBetween(derInfo.getId(), start, end).stream())
                .collect(Collectors.toList());
    }
}
