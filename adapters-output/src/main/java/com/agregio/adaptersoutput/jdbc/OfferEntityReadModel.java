package com.agregio.adaptersoutput.jdbc;

import com.agregio.adaptersoutput.jdbc.entities.ReadOfferEntities;
import com.agregio.domain.coredomain.MarketType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class OfferEntityReadModel {

    private final JdbcTemplate jdbcTemplate;

    public OfferEntityReadModel(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public ReadOfferEntities findOffersWithBlocksWithParksByMarketType(MarketType marketType) {
        String sql = """
            SELECT o.id AS offer_id, o.market_type, 
                   hb.id AS block_id, hb.start_time, hb.duration_hours, hb.quantity, hb.floor_price,
                   p.id AS park_id, p.name AS park_name, p.type AS park_type, p.capacity
            FROM offers o
            LEFT JOIN blocks hb ON o.id = hb.offer_id
            LEFT JOIN block_parks bp ON hb.id = bp.block_id
            LEFT JOIN parks p ON bp.park_id = p.id
            WHERE o.market_type = ?
            """;

        return jdbcTemplate.query(sql, new Object[]{marketType.name()}, new OfferEntitiesResultSetExtractor());
    }
}
