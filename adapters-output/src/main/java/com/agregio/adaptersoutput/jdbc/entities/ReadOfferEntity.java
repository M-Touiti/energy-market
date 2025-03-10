package com.agregio.adaptersoutput.jdbc.entities;

import com.agregio.domain.coredomain.MarketType;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public record ReadOfferEntity(Long id, MarketType marketType, Set<ReadBlockEntity> readBlockEntities) {
    // we can use Map<Long, ReadBlockEntity> readBlockEntities instead of Set
    public ReadOfferEntity(Long id, MarketType marketType, Set<ReadBlockEntity> readBlockEntities) {
        this.id = id;
        this.marketType = marketType;
        this.readBlockEntities = Optional.ofNullable(readBlockEntities)
                .map(HashSet::new)
                .orElse(new HashSet<>());
    }

    public void add(ReadBlockEntity readBlockEntity) {
        if (readBlockEntity.id() != null)
            this.readBlockEntities.add(readBlockEntity);
    }

    public void addBlockAndParks(Set<ReadBlockEntity> readBlockEntities) {
        if ( (readBlockEntities != null) && (!readBlockEntities.isEmpty()) ) {
            for(ReadBlockEntity readOfferEntity: readBlockEntities){
                this.readBlockEntities.stream()
                        .filter(readBlock -> readBlock.id().equals(readOfferEntity.id()))
                        .findFirst()
                        .ifPresentOrElse( block -> {
                                    block.addAll(readOfferEntity.readParkEntities());
                                },
                                () -> this.readBlockEntities.add(readOfferEntity)
                        );
            }
        }
    }
}
