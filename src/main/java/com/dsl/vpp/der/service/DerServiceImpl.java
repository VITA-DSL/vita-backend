package com.dsl.vpp.der.service;

import com.dsl.vpp.der.DerEntity;
import com.dsl.vpp.der.DerMapper;
import com.dsl.vpp.der.DerRepository;
import com.dsl.vpp.der.value.DerInfo;
import com.dsl.vpp.vpp.core.service.VppService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Service
public class DerServiceImpl implements DerService {
    private final VppService vppService;
    private final DerRepository derRepository;

    @Override
    public String create(DerInfo der) {
        validateValue(der);
        return derRepository.save(DerMapper.mapToEntity(der)).getId();
    }

    @Override
    public DerInfo readById(String id) {
        return derRepository.findById(id)
                .map(DerMapper::mapToValue)
                .orElseThrow(()->new NoSuchElementException("존재하지 않는 DER 아이디입니다."));
    }

    @Override
    public List<DerInfo> readAll() {
        return derRepository.findAll().stream()
                .map(DerMapper::mapToValue)
                .toList();
    }

    @Override
    public List<DerInfo> readByVppId(String vppId) {
        return derRepository.findByVppId(vppId).stream()
                .map(DerMapper::mapToValue)
                .toList();
    }

    @Override
    public Integer countByVppId(String vppId) {
        return derRepository.countByVppId(vppId).intValue();
    }

    @Override
    public void deleteById(String id) {
        validateIdExists(id);
        derRepository.deleteById(id);
    }

    @Override
    public void register(String id, String vppId) {
        vppService.validateIdExists(vppId);

        DerEntity der = derRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("존재하지 않는 DER 아이디입니다."));
        der.register(vppId);
        derRepository.save(der);
    }

    @Override
    public void unregister(String id) {
        DerEntity der = derRepository.findById(id)
                .orElseThrow(()->new NoSuchElementException("존재하지 않는 DER 아이디입니다."));
        der.unregister();
        derRepository.save(der);
    }

    @Override
    public void validateIdExists(String id) {
        if(!derRepository.existsById(id)) {
            throw new NoSuchElementException("존재하지 않는 DER 아이디입니다.");
        }
    }

    private void validateValue(DerInfo der) {
        if(der.getCapacity() < 0) {
            throw new IllegalArgumentException("전력 수용량은 0보다 작을 수 없습니다.");
        }
    }
}
