package com.dsl.vpp.generation;

import com.dsl.vpp.generation.dto.request.GenerationPostRequestDto;
import com.dsl.vpp.generation.dto.response.GenerationGetListResponseDto;
import com.dsl.vpp.generation.dto.response.GenerationGetResponseDto;
import com.dsl.vpp.generation.service.GenerationService;
import com.dsl.vpp.generation.value.GenerationInfo;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class GenerationController {
    private final GenerationService generationService;

    @PostMapping("/predictions/{predictionId}/generations")
    public ResponseEntity<String> post(@PathVariable String predictionId, @Valid @RequestBody GenerationPostRequestDto requestDto) {
        GenerationInfo generationInfo = GenerationMapper.mapToValue(predictionId, requestDto);
        String generationId = generationService.create(generationInfo);
        return ResponseEntity.ok(generationId);
    }

    @GetMapping("/predictions/{predictionId}/generations")
    public ResponseEntity<GenerationGetResponseDto> getGenerationByPrediction(String predictionId) {
        GenerationInfo generationInfo = generationService.readByPredictionId(predictionId);
        GenerationGetResponseDto responseDto = GenerationMapper.mapToDto(generationInfo);
        return ResponseEntity.ok().body(responseDto);
    }

    @GetMapping("/ders/{derId}/generations")
    public ResponseEntity<GenerationGetListResponseDto> getGenerationListByDerBetween( // 차후에 Between 은 조건 쿼리로 변경 예정
            @PathVariable String derId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDateTime end
    ) {
        List<GenerationInfo> generationInfos = generationService.readByDerIdBetween(derId, start, end);
        GenerationGetListResponseDto responseDto = GenerationMapper.mapToDto(generationInfos);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/vpps/{vppId}/generations")
    public ResponseEntity<GenerationGetListResponseDto> getGenerationListByVppBetween(
            @PathVariable String vppId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDateTime end
    ) {
        List<GenerationInfo> generationInfos = generationService.readByVppIdBetween(vppId, start, end);
        GenerationGetListResponseDto responseDto = GenerationMapper.mapToDto(generationInfos);
        return ResponseEntity.ok(responseDto);
    }
}
