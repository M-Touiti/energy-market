package com.agregio.adaptersoutput.jdbc.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public record ReadBlockEntity(Long id, LocalDateTime startTime, int durationHours, double quantity, BigDecimal floorPrice, Set<ReadParkEntity> readParkEntities) {

    public ReadBlockEntity(Long id, LocalDateTime startTime, int durationHours, double quantity, BigDecimal floorPrice, Set<ReadParkEntity> readParkEntities) {
        this.id = id;
        this.startTime = startTime;
        this.durationHours = durationHours;
        this.quantity = quantity;
        this.floorPrice = floorPrice;
        this.readParkEntities = Optional.ofNullable(readParkEntities)
                .map(HashSet::new)
                .orElse(new HashSet<>());;
    }

    public void add(ReadParkEntity park) {
        if (park.id() != null)
            this.readParkEntities.add(park);
    }

    public void addAll(Set<ReadParkEntity> parks){
        if ( (parks != null) && (!parks.isEmpty()) ){
            this.readParkEntities.addAll(parks);
        }
    }
}
