package com.dsl.vpp.vpp.core;

import com.dsl.vpp.vpp.allocation.AllocationMapper;
import com.dsl.vpp.vpp.allocation.service.AllocationService;
import com.dsl.vpp.vpp.core.dto.request.VppCreateRequestDto;
import com.dsl.vpp.vpp.core.dto.response.VppReadListResponseDto;
import com.dsl.vpp.vpp.core.dto.response.VppReadResponseDto;
import com.dsl.vpp.vpp.core.service.VppService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class VppController {
    VppService vppService;
    AllocationService allocationService;

    @Autowired
    VppController(VppService vppService, AllocationService allocationService) {
        this.vppService = vppService;
        this.allocationService = allocationService;
    }

    @PostMapping("/vpps")
    ResponseEntity<String> create(@Valid @RequestBody VppCreateRequestDto vppCreateRequest) {
        return ResponseEntity.ok().body(vppService.create(VppMapper.mapToValue(vppCreateRequest)));
    }
    @GetMapping("/vpps/{id}")
    ResponseEntity<VppReadResponseDto> read(@PathVariable String id) {
        return ResponseEntity.ok().body(VppMapper.mapToDto(vppService.readById(id)));
    }
    @GetMapping("/vpps")
    ResponseEntity<VppReadListResponseDto> read() {
        return ResponseEntity.ok().body(VppMapper.mapToDto(vppService.readAll()));
    }

    @PostMapping("/vpps/{id}/ders/{derId}")
    ResponseEntity<Void> allocateDer(@PathVariable String id, @PathVariable String derId) {
        allocationService.allocate(AllocationMapper.mapToValue(id, derId));
        return ResponseEntity.ok().build();
    }
    @DeleteMapping("/vpps/{id}/ders/{derId}")
    ResponseEntity<Void> deallocateDer(@PathVariable String id, @PathVariable String derId) {
        allocationService.deallocate(AllocationMapper.mapToValue(id, derId));
        return ResponseEntity.ok().build();
    }
}
