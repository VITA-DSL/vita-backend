package com.dsl.vpp.settlementRecord.service;

import com.dsl.vpp.der.service.DerService;
import com.dsl.vpp.der.value.DerInfo;
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
    private  final DerService derService;
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
    public List<SettlementRecordInfo> readByDerIdBetween(String derId, LocalDateTime start, LocalDateTime end) {
        return settlementRecordRepository.findByDerIdAndDateTimeBetween(derId, start, end)
                .stream()
                .map(SettlementRecordMapper::mapToValue)
                .toList();
    }

    @Override
    public List<SettlementRecordInfo> readByVppIdBetween(String vppId, LocalDateTime start, LocalDateTime end) {
        List<String> derIds = derService.readByVppId(vppId)
                .stream()
                .map(DerInfo::getId)
                .toList();

        return settlementRecordRepository.findByDerIdInAndDateTimeBetween(derIds, start, end)
                .stream()
                .map(SettlementRecordMapper::mapToValue)
                .toList();
    }
}
