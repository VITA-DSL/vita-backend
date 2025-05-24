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
import java.util.List;

@RestController
@RequiredArgsConstructor
public class GenerationController {
    private final GenerationService generationService;

    @PostMapping("/ders/{id}/gens")
    public ResponseEntity<String> create(
            @PathVariable String id,
            @Valid @RequestBody GenerationCreateRequestDto createRequestDto
    ) {
        GenerationInfo generationInfo = GenerationMapper.mapToValue(id, createRequestDto);
        String generationId = generationService.create(generationInfo);
        return ResponseEntity.ok(generationId);
    }

    @GetMapping("/ders/{id}/gens")
    public ResponseEntity<GenerationReadListResponseDto> getGenerationListByDerBetween(
            @PathVariable String id,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end
    ) {
        List<GenerationInfo> generationInfos = generationService.readByDerBetween(id, start, end);
        GenerationReadListResponseDto responseDto = GenerationMapper.mapToDto(generationInfos);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/vpps/{id}/gens")
    public ResponseEntity<GenerationReadListResponseDto> getGenerationListByVppBetween(
            @PathVariable String id,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end
    ) {
        List<GenerationInfo> generationInfos = generationService.readByVppBetween(id, start, end);
        GenerationReadListResponseDto responseDto = GenerationMapper.mapToDto(generationInfos);
        return ResponseEntity.ok(responseDto);
    }
}
