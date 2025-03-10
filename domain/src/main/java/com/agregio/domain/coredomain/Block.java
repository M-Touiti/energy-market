package com.agregio.domain.coredomain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record Block(Long id, LocalDateTime startTime, int durationHours,
                    double quantity, BigDecimal floorPrice, List<Park> parks) {
}
