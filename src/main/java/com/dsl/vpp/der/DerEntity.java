package com.dsl.vpp.der;

import com.dsl.vpp.vpp.core.VppEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity(name="der")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class DerEntity {
    @Id
    String id;
    Double capacity;
    @ManyToOne
    @JoinColumn(name="vppId")
    VppEntity vpp;

    @PrePersist
    public void assignId() {
        if (this.id == null) {
            this.id = UUID.randomUUID().toString();
        }
    }
    public void register(VppEntity vpp) {
        this.vpp = vpp;
    }
    public void unregister() {
        if(this.vpp == null) throw new IllegalStateException("해당 DER은 소속된 VPP가 없습니다.");
        this.vpp = null;
    }
}
