package com.dsl.vpp.adjustedPrediction;

import com.dsl.vpp.adjustedPrediction.dto.AdjustedPredictionPostRequestDto;
import com.dsl.vpp.adjustedPrediction.dto.response.AdjustedPredictionGetListResponseDto;
import com.dsl.vpp.adjustedPrediction.dto.response.AdjustedPredictionGetResponseDto;
import com.dsl.vpp.adjustedPrediction.service.AdjustedPredictionService;
import com.dsl.vpp.adjustedPrediction.value.AdjustedPredictionInfo;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class AdjustedPredictionController {
    private final AdjustedPredictionService adjustedPredictionService;

    @PostMapping("predictions/{predictionId}/adjusted-prediction")
    public ResponseEntity<String> create(@PathVariable String predictionId, @Valid @RequestBody AdjustedPredictionPostRequestDto requestDto) {
        AdjustedPredictionInfo adjustedPredictionInfo = AdjustedPredictionMapper.mapToValue(predictionId, requestDto);
        String generationId = adjustedPredictionService.create(adjustedPredictionInfo);
        return ResponseEntity.ok(generationId);
    }

    @GetMapping("adjusted-predictions/{id}")
    public ResponseEntity<AdjustedPredictionGetResponseDto> getAdjustedPrediction(@PathVariable String id) {
        AdjustedPredictionInfo adjustedPredictionInfo = adjustedPredictionService.readById(id);
        AdjustedPredictionGetResponseDto responseDto = AdjustedPredictionMapper.mapToDto(adjustedPredictionInfo);
        return ResponseEntity.ok().body(responseDto);
    }

    @GetMapping("predictions/{predictionId}/adjusted-prediction")
    public ResponseEntity<AdjustedPredictionGetResponseDto> getAdjustedPredictionByPrediction(@PathVariable String predictionId) {
        AdjustedPredictionInfo adjustedPredictionInfo = adjustedPredictionService.readByPredictionId(predictionId);
        AdjustedPredictionGetResponseDto responseDto = AdjustedPredictionMapper.mapToDto(adjustedPredictionInfo);
        return ResponseEntity.ok().body(responseDto);
    }

    @GetMapping("/ders/{derId}/adjusted-predictions")
    public ResponseEntity<AdjustedPredictionGetListResponseDto> getAdjustedPredictionListByDerBetween(
            @PathVariable String derId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDateTime end
    ) {
        List<AdjustedPredictionInfo> adjustedPredictionInfoList = adjustedPredictionService.readByDerIdBetween(derId, start, end);
        AdjustedPredictionGetListResponseDto responseDto = AdjustedPredictionMapper.mapToDto(adjustedPredictionInfoList);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/vpps/{vppId}/adjusted-predictions")
    public ResponseEntity<AdjustedPredictionGetListResponseDto> getGenerationListByVppBetween(
            @PathVariable String vppId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDateTime end
    ) {
        List<AdjustedPredictionInfo> adjustedPredictionInfoList = adjustedPredictionService.readByVppIdBetween(vppId, start, end);
        AdjustedPredictionGetListResponseDto responseDto = AdjustedPredictionMapper.mapToDto(adjustedPredictionInfoList);
        return ResponseEntity.ok(responseDto);
    }
}
