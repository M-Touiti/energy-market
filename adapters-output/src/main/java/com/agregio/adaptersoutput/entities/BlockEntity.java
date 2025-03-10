package com.agregio.adaptersoutput.entities;

import com.agregio.domain.coredomain.Block;
import com.agregio.domain.coredomain.Park;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Entity
@Table(name = "blocks")
public class BlockEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime startTime;

    private int durationHours;  // Durée du bloc en heures
    private double quantity; // Quantité d'énergie en MW
    private BigDecimal floorPrice; // Prix plancher en euros

    @ManyToOne
    @JoinColumn(name = "offer_id")
    private OfferEntity offerEntity;

    @ManyToMany
    @JoinTable(
            name = "block_parks",
            joinColumns = @JoinColumn(name = "block_id"),
            inverseJoinColumns = @JoinColumn(name = "park_id")
    )
    private List<ParkEntity> parksEntities = new ArrayList<>();

    //needed for spring data jdbc, otherwise it requests attribute Park
    public BlockEntity() {
    }

    public BlockEntity(Block block) {
        this.id = block.id();
        this.startTime = block.startTime();
        this.durationHours = block.durationHours();
        this.quantity = block.quantity();
        this.floorPrice = block.floorPrice();
        this.parksEntities = block.parks()
                .stream()
                .map(ParkEntity::new)
                .toList();
    }

    public Block toBlock(){
        List<Park> parks = Optional.ofNullable(this.parksEntities)
                .orElse(List.of())
                .stream()
                .filter(Objects::nonNull)
                .map(ParkEntity::toPark)
                .toList();
        return new Block(this.id, this.startTime, this.durationHours, this.quantity, this.floorPrice
        , parks
        );
    }
}
