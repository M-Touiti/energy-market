package com.agregio.adaptersoutput.jdbc.entities;

import com.agregio.domain.coredomain.ParkType;

public record ReadParkEntity(Long id, String name, ParkType type, double capacity) {
}
