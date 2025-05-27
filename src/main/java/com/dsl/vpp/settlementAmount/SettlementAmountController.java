package com.dsl.vpp.settlementAmount;

import com.dsl.vpp.settlementAmount.dto.SettlementAmountGetListResponseDto;
import com.dsl.vpp.settlementAmount.dto.SettlementAmountGetResponseDto;
import com.dsl.vpp.settlementAmount.service.SettlementAmountService;
import com.dsl.vpp.settlementAmount.value.SettlementAmountInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
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
    public ResponseEntity<SettlementAmountGetListResponseDto> getByDer(
            @PathVariable String derId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDateTime end
    ) {
        List<SettlementAmountInfo> settlementAmountInfoList = settlementAmountService.readByDerIdBetween(derId, start, end);
        SettlementAmountGetListResponseDto responseDto = SettlementAmountMapper.mapToDto(settlementAmountInfoList);
        return ResponseEntity.ok().body(responseDto);
    }

    @GetMapping("/vpps/{vppId}/settlement-amounts")
    public ResponseEntity<SettlementAmountGetListResponseDto> getByVpp(
            @PathVariable String vppId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDateTime end
    ) {
        List<SettlementAmountInfo> settlementAmountInfoList = settlementAmountService.readByVppIdBetween(vppId, start, end);
        SettlementAmountGetListResponseDto responseDto = SettlementAmountMapper.mapToDto(settlementAmountInfoList);
        return ResponseEntity.ok().body(responseDto);
    }
}
