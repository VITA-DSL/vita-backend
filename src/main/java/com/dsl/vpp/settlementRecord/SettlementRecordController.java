package com.dsl.vpp.settlementRecord;

import com.dsl.vpp.settlementRecord.dto.SettlementGetListResponseDto;
import com.dsl.vpp.settlementRecord.dto.SettlementGetResponseDto;
import com.dsl.vpp.settlementRecord.service.SettlementRecordService;
import com.dsl.vpp.settlementRecord.value.SettlementRecordInfo;
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
public class SettlementRecordController {
    private final SettlementRecordService settlementRecordService;

    @GetMapping("/settlement-amounts/{id}")
    public ResponseEntity<SettlementGetResponseDto> get(@PathVariable String id) {
        SettlementRecordInfo settlementRecordInfo = settlementRecordService.readById(id);
        SettlementGetResponseDto responseDto = SettlementRecordMapper.mapToDto(settlementRecordInfo);
        return ResponseEntity.ok().body(responseDto);
    }

    @GetMapping("/ders/{derId}/settlements")
    public ResponseEntity<SettlementGetListResponseDto> getByDer(
            @PathVariable String derId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end
    ) {
        List<SettlementRecordInfo> settlementRecordInfoList = settlementRecordService.readByDerIdBetween(derId, start, end);
        SettlementGetListResponseDto responseDto = SettlementRecordMapper.mapToDto(settlementRecordInfoList);
        return ResponseEntity.ok().body(responseDto);
    }

    @GetMapping("/vpps/{vppId}/settlements")
    public ResponseEntity<SettlementGetListResponseDto> getByVpp(
            @PathVariable String vppId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end
    ) {
        List<SettlementRecordInfo> settlementRecordInfoList = settlementRecordService.readByVppIdBetween(vppId, start, end);
        SettlementGetListResponseDto responseDto = SettlementRecordMapper.mapToDto(settlementRecordInfoList);
        return ResponseEntity.ok().body(responseDto);
    }
}
