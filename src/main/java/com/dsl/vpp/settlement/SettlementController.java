package com.dsl.vpp.settlement;

import com.dsl.vpp.settlement.service.SettlementService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@RestController
public class SettlementController {
    private final SettlementService settlementService;

    @PostMapping("/generations/{generationId}/settlement-amounts")
    public ResponseEntity<String> settle(@PathVariable String generationId) {
        String settlementId = settlementService.settle(generationId);
        return ResponseEntity.ok().body(settlementId);
    }

    @PostMapping("/vpps/{vppId}/settlement-amounts")
    public ResponseEntity<Void> settleByVppIdBetween(
            @PathVariable String vppId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end
    ) {
        settlementService.settleByVppIdBetween(vppId, start, end);
        return ResponseEntity.ok().build();
    }
}
