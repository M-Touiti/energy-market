package com.agregio.adaptersoutput;


import com.agregio.domain.coredomain.Block;
import com.agregio.domain.coredomain.Offer;
import com.agregio.domain.coredomain.Park;
import com.agregio.domain.repository.OfferRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.List;

@Repository
public class JdbcOfferEntityRepository implements OfferRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcOfferEntityRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Long createOffer(Offer offer) {
        String sql = "INSERT INTO offers (market_type) VALUES (?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, offer.marketType().name());
            return ps;
        }, keyHolder);

        Long offerId = keyHolder.getKey().longValue();
        createHourlyBlocks(offerId, offer.blocks());
        return offerId;
    }

    private void createHourlyBlocks(Long offerId, List<Block> blocks) {
        String blockSql = "INSERT INTO blocks " +
                "(start_time, duration_hours, quantity, floor_price, offer_id) " +
                "VALUES (?, ?, ?, ?, ?)";

        blocks.forEach(block -> {
            KeyHolder blockKeyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(blockSql, new String[]{"id"});
                ps.setTimestamp(1, Timestamp.valueOf(block.startTime()));
                ps.setInt(2, block.durationHours());
                ps.setDouble(3, block.quantity());
                ps.setBigDecimal(4, block.floorPrice());
                ps.setLong(5, offerId);
                return ps;
            }, blockKeyHolder);

            Long blockId = blockKeyHolder.getKey().longValue();
            linkBlockParks(blockId, block.parks());
        });
    }

    private void linkBlockParks(Long blockId, List<Park> parks) {
        parks.forEach(park -> jdbcTemplate.update(
                "INSERT INTO block_parks (block_id, park_id) VALUES (?, ?)",
                blockId, park.id()));
    }
}
