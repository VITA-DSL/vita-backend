package com.dsl.vpp.generation;

import com.dsl.vpp.generation.dto.request.GenerationCreateRequestDto;
import com.dsl.vpp.generation.dto.response.GenerationReadListResponseDto;
import com.dsl.vpp.generation.service.GenerationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
public class GenerationController {
    GenerationService generationService;

    @Autowired
    GenerationController(GenerationService generationService) {
        this.generationService = generationService;
    }

    @PostMapping("/gens")
    ResponseEntity<String> create(@Valid @RequestBody GenerationCreateRequestDto createRequest) {
        return ResponseEntity.ok().body(generationService.create(GenerationMapper.mapToValue(createRequest)));
    }
    @GetMapping("/gens")
    ResponseEntity<GenerationReadListResponseDto> read(
            @RequestParam(required = false) String derId,
            @RequestParam(required = false) String vppId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end
            ) {
        if (derId != null && vppId != null) {
            throw new IllegalArgumentException("derId와 vppId 중에 하나만 입력해야 합니다.");
        }

        if (derId != null) {
            return ResponseEntity.ok().body(GenerationMapper.mapToDto(generationService.readByDerBetween(derId, start, end)));
        }
        if (vppId != null) {
            return ResponseEntity.ok().body(GenerationMapper.mapToDto(generationService.readByVppBetween(vppId, start, end)));
        }
        return ResponseEntity.ok().body(GenerationMapper.mapToDto(generationService.readBetween(start, end)));
    }
}
