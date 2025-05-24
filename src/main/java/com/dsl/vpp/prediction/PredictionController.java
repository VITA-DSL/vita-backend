package com.dsl.vpp.prediction;

import com.dsl.vpp.prediction.dto.response.PredictionGetListResponseDto;
import com.dsl.vpp.prediction.dto.request.PredictionPostRequestDto;
import com.dsl.vpp.prediction.service.PredictionService;
import com.dsl.vpp.prediction.value.PredictionInfo;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class PredictionController {
    private final PredictionService predictionService;

    @PostMapping("/ders/{derId}/predictions")
    public ResponseEntity<String> post(@PathVariable String derId, @Valid @RequestBody PredictionPostRequestDto requestDto) {
        PredictionInfo predictionInfo = PredictionMapper.mapToValue(derId, requestDto);
        String predictionId = predictionService.create(predictionInfo);
        return ResponseEntity.ok().body(predictionId);
    }

    @GetMapping("/ders/{derId}/predictions")
    public ResponseEntity<PredictionGetListResponseDto> getListByDerBetween(
            @PathVariable String derId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDateTime end
    ) {
        List<PredictionInfo> predictionInfoList = predictionService.readByDerBetween(derId, start, end);
        return ResponseEntity.ok().body(PredictionMapper.mapToDto(predictionInfoList));
    }
}
