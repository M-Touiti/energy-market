package com.agregio.adaptersoutput.entities;

import com.agregio.domain.coredomain.Park;
import com.agregio.domain.coredomain.ParkType;
import jakarta.persistence.*;

@Entity
@Table(name = "parks")
public class ParkEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private ParkType type;

    private double capacity; // en MW

    //needed for spring data jdbc, otherwise it requests attribute Park
    public ParkEntity() {
    }

    public ParkEntity(Park park) {
        this.id = park.id();
        this.name = park.name();
        this.type = park.type();
        this.capacity = park.capacity();
    }

    public Park toPark(){
        return new Park(this.id, this.name, this.type, this.capacity);
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

    public double getCapacity() {
        return capacity;
    }

    public void setCapacity(double capacity) {
        this.capacity = capacity;
    }
}
