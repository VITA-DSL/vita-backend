package com.dsl.vpp.der.service;

import com.dsl.vpp.der.DerEntity;
import com.dsl.vpp.der.DerMapper;
import com.dsl.vpp.der.DerRepository;
import com.dsl.vpp.der.value.DerInfo;
import com.dsl.vpp.vpp.core.VppEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DerServiceImpl implements DerService {
    DerRepository derRepository;

    @Autowired
    public DerServiceImpl(DerRepository derRepository) {
        this.derRepository = derRepository;
    }

    @Override
    public String create(DerInfo der) {
        validateValue(der);
        return derRepository.save(DerMapper.mapToEntity(der)).getId();
    }

    @Override
    public DerInfo readById(String id) {
        DerEntity der = derRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("존재하지 않는 DER 아이디입니다."));
        return DerMapper.mapToValue(der);
    }

    @Override
    public List<DerInfo> readAll() {
        return DerMapper.mapToValue(derRepository.findAll());
    }

    @Override
    public List<DerInfo> readByVppId(String vppId) {
        return DerMapper.mapToValue(derRepository.findByVppId(vppId));
    }

    @Override
    public void deleteById(String id) {
        validateIdExists(id);
        derRepository.deleteById(id);
    }

    @Override
    public void register(String id, String vppId) {
        DerEntity der = derRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("존재하지 않는 DER 아이디입니다."));

        VppEntity vpp = VppEntity.builder().id(vppId).build();
        der.register(vpp);
        derRepository.save(der);
    }

    @Override
    public void unregister(String id) {
        DerEntity der = derRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("존재하지 않는 DER 아이디입니다."));
        der.unregister();
        derRepository.save(der);
    }

    @Override
    public void validateIdExists(String id) {
        if(!derRepository.existsById(id)) {
            throw new IllegalArgumentException("존재하지 않는 DER 아이디입니다.");
        }
    }

    private void validateValue(DerInfo der) {
        if(der.getCapacity() < 0) {
            throw new IllegalArgumentException("전력 수용량은 0보다 작을 수 없습니다.");
        }
    }
}
