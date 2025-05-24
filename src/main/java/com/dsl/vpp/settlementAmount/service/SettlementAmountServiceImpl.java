package com.dsl.vpp.settlementAmount.service;

import com.dsl.vpp.generation.service.GenerationService;
import com.dsl.vpp.generation.value.GenerationInfo;
import com.dsl.vpp.settlementAmount.SettlementAmountEntity;
import com.dsl.vpp.settlementAmount.SettlementAmountMapper;
import com.dsl.vpp.settlementAmount.SettlementAmountRepository;
import com.dsl.vpp.settlementAmount.value.SettlementAmountInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
        SettlementAmountEntity settlementAmountEntity = settlementAmountRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("존재하지 않는 정산 데이터입니다."));
        return SettlementAmountMapper.mapToValue(settlementAmountEntity);
    }

    @Override
    public SettlementAmountInfo readByGenerationId(String generationId) {
        SettlementAmountEntity settlementAmountEntity = settlementAmountRepository.findByGenerationId(generationId)
                .orElseThrow(()->new IllegalArgumentException("존재하지 않는 전력 데이터입니다."));
        return SettlementAmountMapper.mapToValue(settlementAmountEntity);
    }

    @Override
    public List<SettlementAmountInfo> readByDerId(String derId) {
        List<GenerationInfo> generationInfoList = generationService.readByDerId(derId);
        return generationInfoList.stream()
                .map(generationInfo -> tryReadByGenerationIdOrNull(generationInfo.getId()))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    private SettlementAmountInfo tryReadByGenerationIdOrNull(String generationId) {
        try {
            return readByGenerationId(generationId);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
