package com.agregio.applicationservices.service;

import com.agregio.adaptersoutput.jdbc.OfferEntityReadModel;
import com.agregio.adaptersoutput.jdbc.entities.ReadOfferEntities;
import com.agregio.applicationservices.conversion.OfferDtoToOffer;
import com.agregio.applicationservices.conversion.ReadOfferEntitiesToOfferResponseDetails;
import com.agregio.applicationservices.dto.OfferDto;
import com.agregio.applicationservices.dto.OfferResponseDetails;
import com.agregio.applicationservices.exceptions.ParkNotFoundException;
import com.agregio.domain.coredomain.*;
import com.agregio.domain.repository.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OfferService {

    private final OfferRepository offerRepository;

    private final OfferDtoToOffer offerDtoToOffer;

    private final OfferEntityReadModel offerEntityReadModel;

    private final ReadOfferEntitiesToOfferResponseDetails readOfferEntitiesToOfferResponseDetails;

    private final ParkService parkService;


    public OfferService(OfferRepository offerRepository, OfferDtoToOffer offerDtoToOffer,
                        OfferEntityReadModel offerEntityReadModel,
                        ReadOfferEntitiesToOfferResponseDetails readOfferEntitiesToOfferResponseDetails,
                        ParkService parkService) {
        this.offerRepository = offerRepository;
        this.offerDtoToOffer = offerDtoToOffer;
        this.offerEntityReadModel = offerEntityReadModel;
        this.readOfferEntitiesToOfferResponseDetails = readOfferEntitiesToOfferResponseDetails;
        this.parkService = parkService;
    }

    public Long createOffer(OfferDto offerDto) {
        Offer offer = this.offerDtoToOffer.convert(offerDto);

        offer.blocks()
                .stream()
                .flatMap(block -> block.parks().stream())
                .distinct()
                .forEach(park -> parkService.findById(park.id())
                        .orElseThrow(() -> new ParkNotFoundException(park.id()))) ;

        return offerRepository.createOffer(offer);
    }

    public List<OfferResponseDetails> getOffersByMarket(MarketType marketType) {
        return Optional.ofNullable(offerEntityReadModel.findOffersWithBlocksWithParksByMarketType(marketType))
                .map(ReadOfferEntities::readDriftEntities)
                .orElse(List.of())
                .stream()
                .map(this.readOfferEntitiesToOfferResponseDetails::convert)
                .toList();
    }
}
