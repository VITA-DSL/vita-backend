package com.dsl.vpp.der;

import com.dsl.vpp.der.service.DerService;
import com.dsl.vpp.der.service.DerServiceImpl;
import com.dsl.vpp.der.value.DerInfo;
import com.dsl.vpp.vpp.core.service.VppService;
import com.dsl.vpp.vpp.core.service.VppServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TestDerService {
    DerRepository derRepository = mock(DerRepository.class);
    VppService vppService = mock(VppServiceImpl.class);
    DerService derService = new DerServiceImpl(vppService, derRepository);

    @BeforeEach
    void setUp() {
        DerEntity derEntity = DerEntity.builder()
                .id("test")
                .vppId("test")
                .capacity(300.0)
                .build();

        DerEntity derEntity2 = DerEntity.builder()
                .id("not registered test")
                .capacity(300.0)
                .build();

        doReturn(derEntity).when(derRepository).save(any(DerEntity.class));
        doReturn(false).when(derRepository).existsById(any(String.class));
        doReturn(true).when(derRepository).existsById("test");
        doReturn(Optional.of(derEntity)).when(derRepository).findById("test");
        doReturn(Optional.of(derEntity2)).when(derRepository).findById("not registered");
    }

    @Test
    void create() {
        DerInfo derInfo = DerInfo.builder()
                .id("test")
                .vppId("test")
                .capacity(300.0)
                .build();

        Assertions.assertDoesNotThrow(()->derService.create(derInfo));
    }

    @Test
    void readById() {
        Assertions.assertDoesNotThrow(()->derService.readById("test"));
    }

    @Test
    void readByNotExistingId() {
        Assertions.assertThrows(IllegalArgumentException.class, ()->derService.readById("not exist"));
    }

    @Test
    void register() {
        Assertions.assertDoesNotThrow(()->derService.register("test", "test"));
    }

    @Test
    void unregister() {
        Assertions.assertDoesNotThrow(()->derService.unregister("test"));
    }

    @Test
    void unregisterNotRegisteredDer() {
        Assertions.assertThrows(IllegalStateException.class, ()->derService.unregister("not registered"));
    }
}
