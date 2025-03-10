package com.agregio.adaptersoutput.jdbc;

import com.agregio.domain.coredomain.MarketType;
import com.agregio.domain.coredomain.Park;
import com.agregio.domain.coredomain.ParkType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ParkEntityReadModel {

    private final JdbcTemplate jdbcTemplate;

    public ParkEntityReadModel(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Park> getParksByMarket(MarketType marketType) {
        String sql = """
            SELECT DISTINCT p.* FROM parks p
            JOIN block_parks bp ON p.id = bp.park_id
            JOIN blocks ob ON bp.block_id = ob.id
            JOIN offers o ON ob.offer_id = o.id
            WHERE o.market_type = ?
            """;

        return jdbcTemplate.query(sql, (rs, rowNum) ->
                new Park(
                        rs.getLong("id"),
                        rs.getString("name"),
                        ParkType.valueOf(rs.getString("type")),
                        rs.getDouble("capacity")
                ), marketType.name());
    }
}
