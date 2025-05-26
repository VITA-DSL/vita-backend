package com.dsl.vpp.vpp;

import com.dsl.vpp.vpp.core.VppEntity;
import com.dsl.vpp.vpp.core.VppRepository;
import com.dsl.vpp.vpp.core.service.VppService;
import com.dsl.vpp.vpp.core.service.VppServiceImpl;
import com.dsl.vpp.vpp.core.value.VppInfo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TestVppService {
    VppRepository vppRepository = mock(VppRepository.class);
    VppService vppService = new VppServiceImpl(vppRepository);

    @BeforeEach
    void setUp() {
        VppEntity vppEntity = VppEntity.builder().id("test").build();

        doReturn(vppEntity).when(vppRepository).save(any(VppEntity.class));
        doReturn(false).when(vppRepository).existsById(any(String.class));
        doReturn(true).when(vppRepository).existsById("test");
        doReturn(Optional.of(vppEntity)).when(vppRepository).findById("test");
    }

    @Test
    void create() {
        VppInfo vpp = VppInfo.builder().id("not exist").build();
        Assertions.assertDoesNotThrow(()->vppService.create(vpp));
    }

    @Test
    void createAlreadyExistingId() {
        VppInfo vpp = VppInfo.builder().id("test").build();
        Assertions.assertThrows(IllegalArgumentException.class, () -> vppService.create(vpp));
    }

    @Test
    void readById() {
        String id = "test";
        Assertions.assertDoesNotThrow(()->vppService.readById(id));
    }

    @Test
    void readByNotExistingId() {
        String id = "not exist";
        Assertions.assertThrows(IllegalArgumentException.class, () -> vppService.readById(id));
    }

    @Test
    void deleteById() {
        String id = "test";
        Assertions.assertDoesNotThrow(()->vppService.deleteById(id));
    }

    @Test
    void deleteByNotExistingId() {
        String id = "not exist";
        Assertions.assertThrows(IllegalArgumentException.class, () -> vppService.deleteById(id));
    }
}
