package com.dsl.vpp.der;

import com.dsl.vpp.der.dto.request.DerPostRequestDto;
import com.dsl.vpp.der.dto.response.DerGetListResponseDto;
import com.dsl.vpp.der.dto.response.DerGetResponseDto;
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
    public ResponseEntity<String> post(@Valid @RequestBody DerPostRequestDto createRequestDto) {
        DerInfo derInfo = DerMapper.mapToValue(createRequestDto);
        String derId = derService.create(derInfo);
        return ResponseEntity.ok().body(derId);
    }

    @GetMapping("/ders")
    public ResponseEntity<DerGetListResponseDto> getAll() {
        List<DerInfo> derInfoList = derService.readAll();
        DerGetListResponseDto responseDto = DerMapper.mapToDto(derInfoList);
        return ResponseEntity.ok().body(responseDto);
    }

    @GetMapping("/ders/{id}")
    public ResponseEntity<DerGetResponseDto> get(@PathVariable String id) {
        DerInfo derInfo = derService.readById(id);
        DerGetResponseDto responseDto = DerMapper.mapToDto(derInfo);
        return ResponseEntity.ok().body(responseDto);
    }

    @GetMapping("/vpps/{vppId}/ders")
    public ResponseEntity<DerGetListResponseDto> getByVppId(@PathVariable String vppId) {
        List<DerInfo> derInfoList = derService.readByVppId(vppId);
        DerGetListResponseDto responseDto = DerMapper.mapToDto(derInfoList);
        return ResponseEntity.ok().body(responseDto);
    }

    @GetMapping("/vpps/{vppId}/ders/amount")
    public ResponseEntity<Integer> getAmountByVppId(@PathVariable String vppId) {
        return ResponseEntity.ok().body(derService.countByVppId(vppId));
    }

    @DeleteMapping("/ders/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        derService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
