package com.dsl.vpp.weight;

import com.dsl.vpp.weight.service.WeightService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class WeightController {
    private final WeightService weightService;
    /*
    public ResponseEntity<Void> generateWeight(String generationId) {
        weightService.updateWeight(generationId);
        return ResponseEntity.ok().build();
    }
    */
}
