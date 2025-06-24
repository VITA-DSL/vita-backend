package com.dsl.vpp.der.service;

import com.dsl.vpp.der.value.DerInfo;

import java.util.List;

public interface DerService {
    String create(DerInfo der);
    DerInfo readById(String id);
    List<DerInfo> readAll();
    List<DerInfo> readByVppId(String vppId);
    Integer countByVppId(String vppId);
    void deleteById(String id);

    void register(String id, String vppId);
    void unregister(String id);

    void validateIdExists(String id);
}
