package com.dsl.vpp.adjustedPrediction;

import com.dsl.vpp.adjustedPrediction.dto.response.AdjustedPredictionGetListResponseDto;
import com.dsl.vpp.adjustedPrediction.dto.response.AdjustedPredictionGetResponseDto;
import com.dsl.vpp.adjustedPrediction.dto.response.DailyAdjustedPredictionGetListResponseDto;
import com.dsl.vpp.adjustedPrediction.service.AdjustedPredictionService;
import com.dsl.vpp.adjustedPrediction.value.AdjustedPredictionInfo;
import com.dsl.vpp.adjustedPrediction.value.DailyAdjustedPredictionInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class AdjustedPredictionController {
    private final AdjustedPredictionService adjustedPredictionService;

    @PostMapping("vpps/{vppId}/adjusted-predictions")
    public ResponseEntity<Void> generateAdjustedPredictionsByVppIdBetween(
            @PathVariable String vppId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end
    ) {
        adjustedPredictionService.generateAdjustedPredictionsByVppIdBetween(vppId, start, end);
        return ResponseEntity.ok().build();
    }

    @GetMapping("adjusted-predictions/{id}")
    public ResponseEntity<AdjustedPredictionGetResponseDto> getAdjustedPrediction(@PathVariable String id) {
        AdjustedPredictionInfo adjustedPredictionInfo = adjustedPredictionService.readById(id);
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

    @GetMapping("/vpps/{vppId}/adjusted-predictions/daily")
    public ResponseEntity<DailyAdjustedPredictionGetListResponseDto> getGenerationListByVppBetween(
            @PathVariable String vppId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end
    ) {
        List<DailyAdjustedPredictionInfo> dailyAdjustedPredictions = adjustedPredictionService.readDailyByVppIdBetween(vppId, start, end);
        DailyAdjustedPredictionGetListResponseDto responseDto = AdjustedPredictionMapper.mapDailyPredictionsToDto(dailyAdjustedPredictions);
        return ResponseEntity.ok(responseDto);
    }
}
