package com.dsl.vpp.generation;

import com.dsl.vpp.der.service.DerService;
import com.dsl.vpp.generation.service.GenerationService;
import com.dsl.vpp.generation.service.GenerationServiceImpl;
import com.dsl.vpp.generation.value.GenerationInfo;
import com.dsl.vpp.prediction.service.PredictionService;
import com.dsl.vpp.prediction.service.PredictionServiceImpl;
import com.dsl.vpp.weight.service.WeightService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
public class TestGenerationService {
    DerService derService = mock(DerService.class);
    PredictionService predictionService = mock(PredictionServiceImpl.class);
    GenerationRepository generationRepository = mock(GenerationRepository.class);
    WeightService weightService = mock(WeightService.class);
    GenerationService generationService = new GenerationServiceImpl(derService, predictionService, weightService, generationRepository);

    @BeforeEach
    void setUp() {
        GenerationEntity generationEntity = GenerationEntity.builder()
                .id("test")
                .predictionId("test")
                .amount(300.0)
                .dateTime(LocalDateTime.now())
                .build();

        doReturn(generationEntity).when(generationRepository).save(any(GenerationEntity.class));
        doReturn(Optional.of(generationEntity)).when(generationRepository).findById("test");
    }

    @Test
    void create() {
        GenerationInfo generationInfo = GenerationInfo.builder()
                .predictionId("test")
                .amount(300.0)
                .dateTime(LocalDateTime.now())
                .build();

        Assertions.assertDoesNotThrow(()->generationService.create(generationInfo));
    }
}
