package com.dsl.vpp.settlementAmount.service;

import com.dsl.vpp.generation.service.GenerationService;
import com.dsl.vpp.generation.value.GenerationInfo;
import com.dsl.vpp.settlementAmount.SettlementAmountEntity;
import com.dsl.vpp.settlementAmount.SettlementAmountMapper;
import com.dsl.vpp.settlementAmount.SettlementAmountRepository;
import com.dsl.vpp.settlementAmount.value.SettlementAmountInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class SettlementAmountServiceImpl implements SettlementAmountService {
    private final GenerationService generationService;
    private final SettlementAmountRepository settlementAmountRepository;

    @Override
    public String create(SettlementAmountInfo settlementAmountInfo) {
        SettlementAmountEntity settlementAmountEntity = SettlementAmountMapper.mapToEntity(settlementAmountInfo);
        return settlementAmountRepository.save(settlementAmountEntity).getId();
    }

    @Override
    public SettlementAmountInfo readById(String id) {
        return settlementAmountRepository.findById(id)
                .map(SettlementAmountMapper::mapToValue)
                .orElseThrow(()->new IllegalArgumentException("존재하지 않는 정산 데이터입니다."));
    }

    @Override
    public SettlementAmountInfo readByGenerationId(String generationId) {
        return settlementAmountRepository.findByGenerationId(generationId)
                .map(SettlementAmountMapper::mapToValue)
                .orElseThrow(()->new IllegalArgumentException("존재하지 않는 전력 데이터입니다."));
    }

    @Override
    public List<SettlementAmountInfo> readByDerIdBetween(String derId, LocalDateTime start, LocalDateTime end) {
        List<String> generationIdList = generationService.readByDerIdBetween(derId, start, end).stream()
                .map(GenerationInfo::getId)
                .toList();

        return settlementAmountRepository.findByGenerationIdIn(generationIdList).stream()
                .map(SettlementAmountMapper::mapToValue)
                .toList();
    }

    @Override
    public List<SettlementAmountInfo> readByVppIdBetween(String vppId, LocalDateTime start, LocalDateTime end) {
        List<String> generationIdList = generationService.readByVppIdBetween(vppId, start, end).stream()
                .map(GenerationInfo::getId)
                .toList();

        return settlementAmountRepository.findByGenerationIdIn(generationIdList).stream()
                .map(SettlementAmountMapper::mapToValue)
                .toList();
    }
}
