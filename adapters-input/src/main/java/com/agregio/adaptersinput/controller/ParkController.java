package com.agregio.adaptersinput.controller;

import com.agregio.applicationservices.dto.ParkDto;
import com.agregio.applicationservices.service.ParkService;
import com.agregio.domain.coredomain.MarketType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/parks")
public class ParkController {

    private final ParkService parkService;

    public ParkController(ParkService parkService) {
        this.parkService = parkService;
    }

    @PostMapping
    public ResponseEntity<ParkDto> createPark(@RequestBody ParkDto parkDto) {
        Long id = parkService.createPark(parkDto);
        parkDto.setId(id);
        return ResponseEntity.status(HttpStatus.CREATED).body(parkDto);
    }

    @GetMapping("/{marketType}")
    public ResponseEntity<List<ParkDto>> getParksByMarket(@PathVariable MarketType marketType) {
        return ResponseEntity.ok(parkService.getParksByMarket(marketType));
    }
}
