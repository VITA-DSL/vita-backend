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

    @PostMapping("/ders/{id}/gens")
    ResponseEntity<String> create(@PathVariable String id, @Valid @RequestBody GenerationCreateRequestDto createRequestDto) {
        return ResponseEntity.ok().body(generationService.create(GenerationMapper.mapToValue(id, createRequestDto)));
    }

    @GetMapping("/ders/{id}/gens")
    ResponseEntity<GenerationReadListResponseDto> getGenerationListByDerBetween(
            @PathVariable String id,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end
            ) {

        return ResponseEntity.ok().body(GenerationMapper.mapToDto(generationService.readByDerBetween(id, start, end)));
    }
    @GetMapping("/vpps/{id}/gens")
    ResponseEntity<GenerationReadListResponseDto> getGenerationListByVppBetween(
            @PathVariable String id,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end
    ) {
        return ResponseEntity.ok().body(GenerationMapper.mapToDto(generationService.readByVppBetween(id, start, end)));
    }
}
