package com.agregio.applicationservices.dto;

import com.agregio.domain.coredomain.MarketType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

public class OfferDto {
    private Long id;
    private MarketType marketType;
    private List<BlockDto> blocks;

    public OfferDto() {
    }

    public OfferDto(Long id, MarketType marketType, List<BlockDto> blocks) {
        this.id = id;
        this.marketType = marketType;
        this.blocks = blocks;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MarketType getMarketType() {
        return marketType;
    }

    public void setMarketType(MarketType marketType) {
        this.marketType = marketType;
    }

    public List<BlockDto> getBlocks() {
        return blocks;
    }

    public void setBlocks(List<BlockDto> blocks) {
        this.blocks = blocks;
    }
}
