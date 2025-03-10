package com.agregio.adaptersoutput.entities;

import com.agregio.domain.coredomain.*;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Entity
@Table(name = "offers")
public class OfferEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private MarketType marketType;

    @OneToMany(mappedBy = "offerEntity", cascade = CascadeType.ALL)
    private List<BlockEntity> blocksEntities = new ArrayList<>(); // Liste des blocs horaires

    //needed for spring data jdbc, otherwise it requests attribute Park
    public OfferEntity() {
    }

    public OfferEntity(Offer offer) {
        this.id = offer.id();
        this.marketType = offer.marketType();
        this.blocksEntities = offer.blocks()
                .stream()
                .map(BlockEntity::new)
                .toList();
    }

    public Offer toOffer(){
        List<Block> blocks = Optional.ofNullable(this.blocksEntities)
                .orElse(List.of())
                .stream()
                .filter(Objects::nonNull)
                .map(BlockEntity::toBlock)
                .toList();
        return new Offer(this.id, this.marketType, blocks);
    }

    public Long getId() {
        return id;
    }
}
