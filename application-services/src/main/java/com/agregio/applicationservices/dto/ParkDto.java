package com.agregio.applicationservices.dto;

import com.agregio.domain.coredomain.ParkType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


public class ParkDto {
    private Long id;
    private String name;
    private ParkType type;
    private Double capacity; // en MW

    public ParkDto(){
    }

    public ParkDto(Long id, String name, ParkType type, Double capacity) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.capacity = capacity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ParkType getType() {
        return type;
    }

    public void setType(ParkType type) {
        this.type = type;
    }

    public Double getCapacity() {
        return capacity;
    }

    public void setCapacity(Double capacity) {
        this.capacity = capacity;
    }
}
