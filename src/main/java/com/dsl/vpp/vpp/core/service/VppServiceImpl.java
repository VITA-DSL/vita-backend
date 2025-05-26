package com.dsl.vpp.vpp.core.service;

import com.dsl.vpp.vpp.core.VppMapper;
import com.dsl.vpp.vpp.core.VppRepository;
import com.dsl.vpp.vpp.core.value.VppInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class VppServiceImpl implements VppService {
    VppRepository vppRepository;

    @Autowired
    public VppServiceImpl(VppRepository vppRepository) {
        this.vppRepository = vppRepository;
    }

    @Override
    public String create(VppInfo vpp) {
        validateIdNotExists(vpp.getId());
        return vppRepository.save(VppMapper.mapToEntity(vpp)).getId();
    }

    @Override
    public VppInfo readById(String id) {
        return vppRepository.findById(id)
                .map(VppMapper::mapToValue)
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 VPP 아이디입니다."));
    }

    @Override
    public List<VppInfo> readAll() {
        return vppRepository.findAll().stream()
                .map(VppMapper::mapToValue)
                .toList();
    }

    @Override
    public void deleteById(String id) {
        validateIdExists(id);
        vppRepository.deleteById(id);
    }

    @Override
    public void validateIdNotExists(String id) {
        if(vppRepository.existsById(id)) {
            throw new IllegalArgumentException("이미 존재하는 VPP 아이디입니다.");
        }
    }

    @Override
    public void validateIdExists(String id) {
        if(!vppRepository.existsById(id)) {
            throw new NoSuchElementException("존재하지 않는 VPP 아이디입니다.");
        }
    }
}
