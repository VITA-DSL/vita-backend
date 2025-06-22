package com.dsl.vpp.settlementRecord.service;

import com.dsl.vpp.generation.service.GenerationService;
import com.dsl.vpp.generation.value.GenerationInfo;
import com.dsl.vpp.settlementRecord.SettlementRecordEntity;
import com.dsl.vpp.settlementRecord.SettlementRecordMapper;
import com.dsl.vpp.settlementRecord.SettlementRecordRepository;
import com.dsl.vpp.settlementRecord.value.SettlementRecordInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class SettlementRecordServiceImpl implements SettlementRecordService {
    private final GenerationService generationService;
    private final SettlementRecordRepository settlementRecordRepository;

    @Override
    public String create(SettlementRecordInfo settlementRecordInfo) {
        SettlementRecordEntity settlementRecordEntity = SettlementRecordMapper.mapToEntity(settlementRecordInfo);
        return settlementRecordRepository.save(settlementRecordEntity).getId();
    }

    @Override
    public SettlementRecordInfo readById(String id) {
        return settlementRecordRepository.findById(id)
                .map(SettlementRecordMapper::mapToValue)
                .orElseThrow(()->new IllegalArgumentException("존재하지 않는 정산 데이터입니다."));
    }

    @Override
    public SettlementRecordInfo readByGenerationId(String generationId) {
        return settlementRecordRepository.findByGenerationId(generationId)
                .map(SettlementRecordMapper::mapToValue)
                .orElseThrow(()->new IllegalArgumentException("존재하지 않는 전력 데이터입니다."));
    }

    @Override
    public List<SettlementRecordInfo> readByDerIdBetween(String derId, LocalDateTime start, LocalDateTime end) {
        List<String> generationIdList = generationService.readByDerIdBetween(derId, start, end).stream()
                .map(GenerationInfo::getId)
                .toList();

        return settlementRecordRepository.findByGenerationIdIn(generationIdList).stream()
                .map(SettlementRecordMapper::mapToValue)
                .toList();
    }

    @Override
    public List<SettlementRecordInfo> readByVppIdBetween(String vppId, LocalDateTime start, LocalDateTime end) {
        List<String> generationIdList = generationService.readByVppIdBetween(vppId, start, end).stream()
                .map(GenerationInfo::getId)
                .toList();

        return settlementRecordRepository.findByGenerationIdIn(generationIdList).stream()
                .map(SettlementRecordMapper::mapToValue)
                .toList();
    }
}
