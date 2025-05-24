package com.dsl.vpp.vpp.core;

import com.dsl.vpp.vpp.allocation.AllocationMapper;
import com.dsl.vpp.vpp.allocation.service.AllocationService;
import com.dsl.vpp.vpp.allocation.value.AllocationInfo;
import com.dsl.vpp.vpp.core.dto.request.VppCreateRequestDto;
import com.dsl.vpp.vpp.core.dto.response.VppReadListResponseDto;
import com.dsl.vpp.vpp.core.dto.response.VppReadResponseDto;
import com.dsl.vpp.vpp.core.service.VppService;
import com.dsl.vpp.vpp.core.value.VppInfo;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
public class VppController {
    private final VppService vppService;
    private final AllocationService allocationService;

    @PostMapping("/vpps")
    public ResponseEntity<String> create(@Valid @RequestBody VppCreateRequestDto vppCreateRequest) {
        VppInfo vppInfo = VppMapper.mapToValue(vppCreateRequest);
        String vppId = vppService.create(vppInfo);
        return ResponseEntity.ok().body(vppId);
    }

    @GetMapping("/vpps")
    public ResponseEntity<VppReadListResponseDto> read() {
        List<VppInfo> vppInfoList = vppService.readAll();
        VppReadListResponseDto responseDto = VppMapper.mapToDto(vppInfoList);
        return ResponseEntity.ok().body(responseDto);
    }

    @GetMapping("/vpps/{id}")
    public ResponseEntity<VppReadResponseDto> read(@PathVariable String id) {
        VppInfo vppInfo = vppService.readById(id);
        VppReadResponseDto responseDto = VppMapper.mapToDto(vppInfo);
        return ResponseEntity.ok().body(responseDto);
    }

    @DeleteMapping("/vpps/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        vppService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/vpps/{id}/ders/{derId}")
    public ResponseEntity<Void> allocateDer(@PathVariable String id, @PathVariable String derId) {
        AllocationInfo allocationInfo = AllocationMapper.mapToValue(id, derId);
        allocationService.allocate(allocationInfo);
        return ResponseEntity.ok().build();
    }
    @DeleteMapping("/vpps/{id}/ders/{derId}")
    public ResponseEntity<Void> deallocateDer(@PathVariable String id, @PathVariable String derId) {
        AllocationInfo allocationInfo = AllocationMapper.mapToValue(id, derId);
        allocationService.deallocate(allocationInfo);
        return ResponseEntity.ok().build();
    }
}
