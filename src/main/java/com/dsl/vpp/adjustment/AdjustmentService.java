package com.dsl.vpp.adjustment;

import java.time.LocalDateTime;

public interface AdjustmentService {
    Double adjust(String derId, LocalDateTime dateTime);
}
