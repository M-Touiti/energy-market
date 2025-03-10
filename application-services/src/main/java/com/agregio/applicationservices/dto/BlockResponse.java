package com.agregio.applicationservices.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

public record BlockResponse(Long id, LocalDateTime startTime, int durationHours, double quantity, BigDecimal floorPrice, Set<ParkResponse> parks) {
}
