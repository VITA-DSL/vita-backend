package com.dsl.vpp.der;

import com.dsl.vpp.der.dto.request.DerCreateRequestDto;
import com.dsl.vpp.der.dto.response.DerReadListResponseDto;
import com.dsl.vpp.der.dto.response.DerReadResponseDto;
import com.dsl.vpp.der.service.DerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class DerController {
    DerService derService;

    @Autowired
    DerController(DerService derService) {
        this.derService = derService;
    }

    @PostMapping("/ders")
    ResponseEntity<String> create(@Valid @RequestBody DerCreateRequestDto createRequestDto) {
        return ResponseEntity.ok().body(derService.create(DerMapper.mapToValue(createRequestDto)));
    }

    @GetMapping("/ders")
    ResponseEntity<DerReadListResponseDto> read() {
        return ResponseEntity.ok().body(DerMapper.mapToDto(derService.readAll()));
    }

    @GetMapping("/ders/{id}")
    ResponseEntity<DerReadResponseDto> read(@PathVariable String id) {
        return ResponseEntity.ok().body(DerMapper.mapToDto(derService.readById(id)));
    }

    @GetMapping("/vpps/{vppId}/ders")
    ResponseEntity<DerReadListResponseDto> readByVppId(@PathVariable String vppId) {
        return ResponseEntity.ok().body(DerMapper.mapToDto(derService.readByVppId(vppId)));
    }

    @DeleteMapping("/ders/{id}")
    ResponseEntity<Void> delete(@PathVariable String id) {
        derService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
