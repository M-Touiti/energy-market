package com.agregio.applicationservices.dto;

import com.agregio.domain.coredomain.MarketType;

import java.util.Set;

public record OfferResponseDetails(Long id, MarketType marketType, Set<BlockResponse> blocks) {
}
