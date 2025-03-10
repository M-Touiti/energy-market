package com.agregio.applicationservices.dto;

import com.agregio.domain.coredomain.ParkType;

public record ParkResponse(Long id, String name, ParkType type, double capacity) {
}
