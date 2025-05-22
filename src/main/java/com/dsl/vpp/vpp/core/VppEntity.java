package com.dsl.vpp.vpp.core;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity(name="vpp")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class VppEntity {
    @Id
    String id;
}
