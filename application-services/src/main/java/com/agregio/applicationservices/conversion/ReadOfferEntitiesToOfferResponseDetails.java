package com.agregio.applicationservices.conversion;

import com.agregio.adaptersoutput.jdbc.entities.ReadBlockEntity;
import com.agregio.adaptersoutput.jdbc.entities.ReadOfferEntity;
import com.agregio.adaptersoutput.jdbc.entities.ReadParkEntity;
import com.agregio.applicationservices.dto.BlockResponse;
import com.agregio.applicationservices.dto.OfferResponseDetails;
import com.agregio.applicationservices.dto.ParkResponse;
import org.springframework.stereotype.Component;

import java.security.InvalidParameterException;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ReadOfferEntitiesToOfferResponseDetails {

    private static final String FIELD_PARAMETER_CANNOT_BE_NULL = "Field parameter cannot be null";

    public OfferResponseDetails convert(ReadOfferEntity readOfferEntity){
        return Optional.ofNullable(readOfferEntity)
                .map(OfferEntity -> new OfferResponseDetails(
                        OfferEntity.id(),
                        OfferEntity.marketType(),
                        getBlocks(OfferEntity.readBlockEntities()))
                )
                .orElseThrow(() -> new InvalidParameterException(FIELD_PARAMETER_CANNOT_BE_NULL));
    }

    public Set<BlockResponse> getBlocks(Set<ReadBlockEntity> readBlockEntities){
        return Optional.ofNullable(readBlockEntities)
                .orElse(Set.of())
                .stream()
                .map(blockEntity -> new BlockResponse(
                        blockEntity.id(),
                        blockEntity.startTime(),
                        blockEntity.durationHours(),
                        blockEntity.quantity(),
                        blockEntity.floorPrice(),
                        getParks(blockEntity.readParkEntities()))
                )
                .collect(Collectors.toSet());
    }

    public Set<ParkResponse> getParks(Set<ReadParkEntity> readParkEntities){
        return Optional.ofNullable(readParkEntities)
                .orElse(Set.of())
                .stream()
                .map(parkEntity -> new ParkResponse(
                        parkEntity.id(),
                        parkEntity.name(),
                        parkEntity.type(),
                        parkEntity.capacity())
                )
                .collect(Collectors.toSet());
    }
}
