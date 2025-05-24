package com.dsl.vpp.der;

import com.dsl.vpp.der.dto.request.DerCreateRequestDto;
import com.dsl.vpp.der.dto.response.DerReadListResponseDto;
import com.dsl.vpp.der.dto.response.DerReadResponseDto;
import com.dsl.vpp.der.service.DerService;
import com.dsl.vpp.der.value.DerInfo;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class DerController {
    private final DerService derService;

    @PostMapping("/ders")
    public ResponseEntity<String> post(@Valid @RequestBody DerCreateRequestDto createRequestDto) {
        DerInfo derInfo = DerMapper.mapToValue(createRequestDto);
        String derId = derService.create(derInfo);
        return ResponseEntity.ok().body(derId);
    }

    @GetMapping("/ders")
    public ResponseEntity<DerReadListResponseDto> getAll() {
        List<DerInfo> derInfoList = derService.readAll();
        DerReadListResponseDto responseDto = DerMapper.mapToDto(derInfoList);
        return ResponseEntity.ok().body(responseDto);
    }

    @GetMapping("/ders/{id}")
    public ResponseEntity<DerReadResponseDto> get(@PathVariable String id) {
        DerInfo derInfo = derService.readById(id);
        DerReadResponseDto responseDto = DerMapper.mapToDto(derInfo);
        return ResponseEntity.ok().body(responseDto);
    }

    @GetMapping("/vpps/{vppId}/ders")
    public ResponseEntity<DerReadListResponseDto> getByVppId(@PathVariable String vppId) {
        List<DerInfo> derInfoList = derService.readByVppId(vppId);
        DerReadListResponseDto responseDto = DerMapper.mapToDto(derInfoList);
        return ResponseEntity.ok().body(responseDto);
    }

    @DeleteMapping("/ders/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        derService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
