package com.dsl.vpp.settlement;

import com.dsl.vpp.settlement.service.SettlementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class SettlementController {
    private final SettlementService settlementService;

    @PostMapping("/ders/{derId}/generations/{generationId}/settlement-amounts")
    public ResponseEntity<String> settleDer(@PathVariable String derId, @PathVariable String generationId) {
        String settlementId = settlementService.settle(derId, generationId);
        return ResponseEntity.ok().body(settlementId);
    }
}
