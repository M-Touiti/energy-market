package com.agregio.applicationservices.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class BlockDto {
    private Long id;
    private LocalDateTime startTime;
    private int durationHours;
    private double quantity;
    private BigDecimal floorPrice;
    private List<ParkDto> parks;

    public BlockDto() {
    }

    public BlockDto(Long id, LocalDateTime startTime, int durationHours, double quantity, BigDecimal floorPrice, List<ParkDto> parks) {
        this.id = id;
        this.startTime = startTime;
        this.durationHours = durationHours;
        this.quantity = quantity;
        this.floorPrice = floorPrice;
        this.parks = parks;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public int getDurationHours() {
        return durationHours;
    }

    public void setDurationHours(int durationHours) {
        this.durationHours = durationHours;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getFloorPrice() {
        return floorPrice;
    }

    public void setFloorPrice(BigDecimal floorPrice) {
        this.floorPrice = floorPrice;
    }

    public List<ParkDto> getParks() {
        return parks;
    }

    public void setParks(List<ParkDto> parks) {
        this.parks = parks;
    }
}
