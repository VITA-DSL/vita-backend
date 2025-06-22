package com.dsl.vpp.weight;

import com.dsl.vpp.weight.service.WeightService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@RestController
public class WeightController {
    private final WeightService weightService;

    @PostMapping("/vpps/{vppId}/weights")
    public ResponseEntity<Void> generateWeightsByVppIdBetween(
            @PathVariable String vppId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end
    ) {
        weightService.generateWeightsByVppIdBetween(vppId, start, end);
        return ResponseEntity.ok().build();
    }
}
