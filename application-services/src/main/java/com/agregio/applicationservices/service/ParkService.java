package com.agregio.applicationservices.service;

import com.agregio.adaptersoutput.jdbc.ParkEntityReadModel;
import com.agregio.applicationservices.conversion.OfferDtoToOffer;
import com.agregio.applicationservices.dto.ParkDto;
import com.agregio.domain.coredomain.MarketType;
import com.agregio.domain.coredomain.Park;
import com.agregio.domain.repository.ParkRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ParkService {

    private final OfferDtoToOffer offerDtoToOffer;

    private final ParkEntityReadModel parkEntityReadModel;

    private final ParkRepository parkRepository;

    public ParkService(OfferDtoToOffer offerDtoToOffer, ParkRepository parkRepository,
                       ParkEntityReadModel parkEntityReadModel) {
        this.offerDtoToOffer = offerDtoToOffer;
        this.parkRepository = parkRepository;
        this.parkEntityReadModel = parkEntityReadModel;
    }

    public Long createPark(ParkDto parkDto) {
        Park park = this.offerDtoToOffer.convert(parkDto);
        return parkRepository.createPark(park);
    }

    public List<ParkDto> getParksByMarket(MarketType marketType) {
        return Optional.ofNullable(parkEntityReadModel.getParksByMarket(marketType))
                .orElse(List.of())
                .stream()
                .map(this.offerDtoToOffer::convert)
                .toList();
    }

    public Optional<Park> findById(Long id){
        return parkRepository.findById(id);
    }
}
