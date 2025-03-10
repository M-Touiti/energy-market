package com.agregio.domain.coredomain;

import java.util.List;

public record Offer(Long id, MarketType marketType, List<Block> blocks) {
}
