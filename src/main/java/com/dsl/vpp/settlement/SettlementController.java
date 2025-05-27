package com.dsl.vpp.settlement;

import com.dsl.vpp.settlement.service.SettlementService;
import com.dsl.vpp.settlementAmount.SettlementAmountMapper;
import com.dsl.vpp.settlementAmount.dto.SettlementAmountGetResponseDto;
import com.dsl.vpp.settlementAmount.value.SettlementAmountInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class SettlementController {
    private final SettlementService settlementService;

    @PostMapping("/generations/{generationId}/settlement-amounts")
    public ResponseEntity<String> settle(@PathVariable String generationId) {
        String settlementId = settlementService.settle(generationId);
        return ResponseEntity.ok().body(settlementId);
    }

    @GetMapping("/generations/{generationId}/simulated-settlement-amounts")
    public ResponseEntity<SettlementAmountGetResponseDto> simulateByGeneration(@PathVariable String generationId) {
        SettlementAmountInfo settlementAmountInfo = settlementService.simulateWithOriginalPrediction(generationId);
        SettlementAmountGetResponseDto responseDto = SettlementAmountMapper.mapToDto(settlementAmountInfo);
        return ResponseEntity.ok().body(responseDto);
    }
}
