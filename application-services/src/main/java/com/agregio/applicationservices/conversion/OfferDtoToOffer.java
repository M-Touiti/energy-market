package com.agregio.applicationservices.conversion;

import com.agregio.applicationservices.dto.*;
import com.agregio.domain.coredomain.*;
import org.springframework.stereotype.Component;

import java.security.InvalidParameterException;
import java.util.List;
import java.util.Optional;

@Component
public class OfferDtoToOffer {

    private static final String FIELD_PARAMETER_CANNOT_BE_NULL = "Field parameter cannot be null";

    public Offer convert(OfferDto offerDto){
        return Optional.ofNullable(offerDto)
                .map(offerdto -> new Offer(
                        offerdto.getId(),
                        offerdto.getMarketType(),
                        convertToBlocks(offerdto.getBlocks())
                ))
                .orElseThrow(() -> new InvalidParameterException(FIELD_PARAMETER_CANNOT_BE_NULL));
    }

    public List<Block> convertToBlocks(List<BlockDto> blockDtos){
        return Optional.ofNullable(blockDtos)
                .map(blockDtoList -> blockDtoList.stream()
                        .map(this::convert)
                        .toList()
                )
                .orElse(null);
    }

    public Block convert(BlockDto blockDto){
        return Optional.ofNullable(blockDto)
                .map(blockdto -> new Block(blockdto.getId(), blockdto.getStartTime(), blockdto.getDurationHours(),
                        blockdto.getQuantity(), blockdto.getFloorPrice(),
                        convertToParks(blockdto.getParks())
                ))
                .orElse(null);
    }

    public List<Park> convertToParks(List<ParkDto> parkDtos){
        return Optional.ofNullable(parkDtos)
                .map(parkDtoList -> parkDtoList.stream()
                        .map(this::convert)
                        .toList()
                )
                .orElse(null);
    }

    public Park convert(ParkDto parkDto){
        return Optional.ofNullable(parkDto)
                .map(parkdto -> new Park(parkdto.getId(), parkdto.getName(), parkdto.getType(), convert(parkdto.getCapacity())))
                .orElse(null);
    }

    public ParkDto convert(Park park){
        return Optional.ofNullable(park)
                .map(parkObject -> new ParkDto(parkObject.id(), parkObject.name(), parkObject.type(), parkObject.capacity()))
                .orElse(null);
    }

    private double convert(Double value){
        return Optional.ofNullable(value)
                .orElse(0.0);
    }
}
