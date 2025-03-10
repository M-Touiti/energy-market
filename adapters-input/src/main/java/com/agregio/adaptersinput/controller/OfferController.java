package com.agregio.adaptersinput.controller;

import com.agregio.applicationservices.dto.OfferDto;
import com.agregio.applicationservices.dto.OfferResponseDetails;
import com.agregio.applicationservices.service.OfferService;
import com.agregio.domain.coredomain.MarketType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/offers")
public class OfferController {

    private final OfferService offerService;

    public OfferController(OfferService offerService) {
        this.offerService = offerService;
    }

    @PostMapping
    public ResponseEntity<OfferDto> createOffer(@RequestBody OfferDto offerDto) {
        Long id = offerService.createOffer(offerDto);
        offerDto.setId(id);
        return ResponseEntity.status(HttpStatus.CREATED).body(offerDto);
    }

    @GetMapping("/{marketType}")
    public List<OfferResponseDetails> getOffersByMarket(@PathVariable MarketType marketType) {
        return offerService.getOffersByMarket(marketType);
    }
}
