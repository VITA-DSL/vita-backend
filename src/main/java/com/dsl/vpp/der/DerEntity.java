package com.dsl.vpp.der;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@Entity(name="der")
public class DerEntity {
    @Id
    String id;
    String vppId; // FK
    Double capacity;

    @PrePersist
    public void assignId() {
        if (this.id == null) {
            this.id = UUID.randomUUID().toString();
        }
    }
    public void register(String vppId) {
        this.vppId = vppId;
    }
    public void unregister() {
        if(this.vppId == null) throw new IllegalStateException("해당 DER은 소속된 VPP가 없습니다.");
        this.vppId = null;
    }
}
