package com.dsl.vpp.der;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DerRepository extends JpaRepository<DerEntity, String> {
    List<DerEntity> findByVppId(String vppId);
    Long countByVppId(String vppId);
}
