package com.dsl.vpp.generation;

import com.dsl.vpp.generation.dto.request.GenerationCreateRequestDto;
import com.dsl.vpp.generation.dto.response.GenerationReadListResponseDto;
import com.dsl.vpp.generation.service.GenerationService;
import com.dsl.vpp.generation.value.GenerationInfo;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class GenerationController {
    private final GenerationService generationService;

    @PostMapping("/ders/{derId}/generations")
    public ResponseEntity<String> create(@PathVariable String derId, @Valid @RequestBody GenerationCreateRequestDto requestDto) {
        GenerationInfo generationInfo = GenerationMapper.mapToValue(derId, requestDto);
        String generationId = generationService.create(generationInfo);
        return ResponseEntity.ok(generationId);
    }

    @GetMapping("/ders/{derId}/generations")
    public ResponseEntity<GenerationReadListResponseDto> getGenerationListByDerBetween(
            @PathVariable String derId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDateTime end
    ) {
        List<GenerationInfo> generationInfos = generationService.readByDerBetween(derId, start, end);
        GenerationReadListResponseDto responseDto = GenerationMapper.mapToDto(generationInfos);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/vpps/{vppId}/gens")
    public ResponseEntity<GenerationReadListResponseDto> getGenerationListByVppBetween(
            @PathVariable String vppId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDateTime end
    ) {
        List<GenerationInfo> generationInfos = generationService.readByVppBetween(vppId, start, end);
        GenerationReadListResponseDto responseDto = GenerationMapper.mapToDto(generationInfos);
        return ResponseEntity.ok(responseDto);
    }
}
