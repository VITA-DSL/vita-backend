package com.dsl.vpp.settlementAmount;

import com.dsl.vpp.settlementAmount.dto.SettlementAmountGetListResponseDto;
import com.dsl.vpp.settlementAmount.dto.SettlementAmountGetResponseDto;
import com.dsl.vpp.settlementAmount.service.SettlementAmountService;
import com.dsl.vpp.settlementAmount.value.SettlementAmountInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class SettlementAmountController {
    private final SettlementAmountService settlementAmountService;

    @GetMapping("/settlement-amounts/{id}")
    public ResponseEntity<SettlementAmountGetResponseDto> get(@PathVariable String id) {
        SettlementAmountInfo settlementAmountInfo = settlementAmountService.readById(id);
        SettlementAmountGetResponseDto responseDto = SettlementAmountMapper.mapToDto(settlementAmountInfo);
        return ResponseEntity.ok().body(responseDto);
    }

    @GetMapping("/generations/{generationId}/settlement-amounts")
    public ResponseEntity<SettlementAmountGetResponseDto> getByGeneration(@PathVariable String generationId) {
        SettlementAmountInfo settlementAmountInfo = settlementAmountService.readByGenerationId(generationId);
        SettlementAmountGetResponseDto responseDto = SettlementAmountMapper.mapToDto(settlementAmountInfo);
        return ResponseEntity.ok().body(responseDto);
    }

    @GetMapping("/ders/{derId}/settlement-amounts")
    public ResponseEntity<SettlementAmountGetListResponseDto> getByDer(@PathVariable String derId) {
        List<SettlementAmountInfo> settlementAmountInfoList = settlementAmountService.readByDerId(derId);
        SettlementAmountGetListResponseDto responseDto = SettlementAmountMapper.mapToDto(settlementAmountInfoList);
        return ResponseEntity.ok().body(responseDto);
    }
}
