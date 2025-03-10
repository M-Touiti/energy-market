package com.agregio.adaptersoutput.jdbc;

import com.agregio.adaptersoutput.jdbc.entities.ReadBlockEntity;
import com.agregio.adaptersoutput.jdbc.entities.ReadOfferEntities;
import com.agregio.adaptersoutput.jdbc.entities.ReadOfferEntity;
import com.agregio.adaptersoutput.jdbc.entities.ReadParkEntity;
import com.agregio.domain.coredomain.*;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class OfferEntitiesResultSetExtractor implements ResultSetExtractor<ReadOfferEntities> {
    @Override
    public ReadOfferEntities extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        List<ReadOfferEntity> entities = new ArrayList<>();

        while(resultSet.next()){
            ReadOfferEntity extractedReadDriftEntity = extractResultData(resultSet);
            entities.stream()
                    .filter(readDriftEntity -> readDriftEntity.id().equals(extractedReadDriftEntity.id()))
                    .findFirst()
                    .ifPresentOrElse( readDriftEntity -> {
                                    readDriftEntity.addBlockAndParks(extractedReadDriftEntity.readBlockEntities());
                            },
                            () -> entities.add(extractedReadDriftEntity)
                    );
        }
        return new ReadOfferEntities(entities);
    }

    public ReadOfferEntity extractResultData(ResultSet resultSet) throws SQLException {
        ReadOfferEntity readDriftEntity;

        Long offerId = resultSet.getLong("offer_id");
        MarketType marketType = MarketType.valueOf(resultSet.getString("market_type"));
        Long blockId = resultSet.getLong("block_id");
        LocalDateTime startTime = resultSet.getObject("start_time", LocalDateTime.class);
        int durationHours = resultSet.getInt("duration_hours");
        double quantity = resultSet.getDouble("quantity");
        BigDecimal floorPrice = resultSet.getBigDecimal("floor_price");
        Long parkId = resultSet.getLong("park_id");
        String parkName = resultSet.getString("park_name");
        ParkType parkType = ParkType.valueOf(resultSet.getString("park_type"));
        double capacity = resultSet.getDouble("capacity");

        ReadParkEntity readParkEntity = new ReadParkEntity(parkId, parkName, parkType, capacity);
        ReadBlockEntity readBlockEntity = new ReadBlockEntity(blockId, startTime, durationHours, quantity, floorPrice, Set.of(readParkEntity));
        readDriftEntity = new ReadOfferEntity(offerId, marketType, Set.of(readBlockEntity));

        return readDriftEntity;
    }
}
