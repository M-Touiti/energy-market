package com.agregio.domain.repository;

import com.agregio.domain.coredomain.Park;

import java.util.Optional;

public interface ParkRepository {

    Long createPark(Park park) ;

    Optional<Park> findById(Long id);
}
