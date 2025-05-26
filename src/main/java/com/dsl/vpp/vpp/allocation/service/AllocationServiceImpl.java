package com.dsl.vpp.vpp.allocation.service;

import com.dsl.vpp.der.service.DerService;
import com.dsl.vpp.vpp.core.service.VppService;
import com.dsl.vpp.vpp.allocation.value.AllocationInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AllocationServiceImpl implements AllocationService {
    private final VppService vppService;
    private final DerService derService;

    @Override
    public void allocate(AllocationInfo allocation) {
        vppService.validateIdExists(allocation.getVppId());
        derService.register(allocation.getDerId(), allocation.getVppId());
    }

    @Override
    public void deallocate(AllocationInfo allocation) {
        derService.unregister(allocation.getDerId());
    }
}
