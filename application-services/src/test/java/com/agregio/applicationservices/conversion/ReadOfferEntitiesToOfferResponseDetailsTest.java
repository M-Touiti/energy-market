package com.agregio.applicationservices.conversion;

import com.agregio.adaptersoutput.jdbc.entities.ReadBlockEntity;
import com.agregio.adaptersoutput.jdbc.entities.ReadOfferEntity;
import com.agregio.adaptersoutput.jdbc.entities.ReadParkEntity;
import com.agregio.applicationservices.dto.BlockResponse;
import com.agregio.applicationservices.dto.OfferResponseDetails;
import com.agregio.applicationservices.dto.ParkResponse;
import com.agregio.domain.coredomain.MarketType;
import com.agregio.domain.coredomain.ParkType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.security.InvalidParameterException;
import java.time.LocalDateTime;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReadOfferEntitiesToOfferResponseDetailsTest {

    @InjectMocks
    private ReadOfferEntitiesToOfferResponseDetails transformer;

    @Test
    public void testConvert_Success() {
        // Arrange
        ReadOfferEntity readOfferEntity = new ReadOfferEntity(
                1L,
                MarketType.RESERVE_PRIMAIRE,
                Set.of(new ReadBlockEntity(
                        1L,
                        LocalDateTime.of(2025, 03, 10, 8, 0),
                        3,
                        100.0,
                        new BigDecimal(50.0),
                        Set.of(new ReadParkEntity(
                                1L,
                                "Solar Park 1",
                                ParkType.SOLAR,
                                50.0
                        ))
                ))
        );

        // Act
        OfferResponseDetails result = transformer.convert(readOfferEntity);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.id());
        assertEquals(MarketType.RESERVE_PRIMAIRE, result.marketType());
        assertEquals(1, result.blocks().size());

        BlockResponse blockResponse = result.blocks().iterator().next();
        assertEquals(1L, blockResponse.id());
        assertEquals(LocalDateTime.of(2025, 03, 10, 8, 0), blockResponse.startTime());
        assertEquals(3, blockResponse.durationHours());
        assertEquals(100.0, blockResponse.quantity());
        assertEquals(50.0, blockResponse.floorPrice().doubleValue());
        assertEquals(1, blockResponse.parks().size());

        ParkResponse parkResponse = blockResponse.parks().iterator().next();
        assertEquals(1L, parkResponse.id());
        assertEquals("Solar Park 1", parkResponse.name());
        assertEquals(ParkType.SOLAR, parkResponse.type());
        assertEquals(50.0, parkResponse.capacity());
    }

    @Test
    public void testConvert_NullInput() {
        // Arrange
        ReadOfferEntity readOfferEntity = null;

        // Act & Assert
        InvalidParameterException exception = assertThrows(InvalidParameterException.class, () -> transformer.convert(readOfferEntity));
        assertEquals("Field parameter cannot be null", exception.getMessage());
    }

    @Test
    public void testGetBlocks_Success() {
        // Arrange
        Set<ReadBlockEntity> readBlockEntities = Set.of(
                new ReadBlockEntity(
                        1L,
                        LocalDateTime.of(2025, 03, 10, 8, 0),
                        3,
                        100.0,
                        new BigDecimal(50.0),
                        Set.of(new ReadParkEntity(
                                1L,
                                "Solar Park 1",
                                ParkType.SOLAR,
                                50.0
                        ))
                )
        );

        // Act
        Set<BlockResponse> result = transformer.getBlocks(readBlockEntities);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());

        BlockResponse blockResponse = result.iterator().next();
        assertEquals(1L, blockResponse.id());
        assertEquals(LocalDateTime.of(2025, 03, 10, 8, 0), blockResponse.startTime());
        assertEquals(3, blockResponse.durationHours());
        assertEquals(100.0, blockResponse.quantity());
        assertEquals(50.0, blockResponse.floorPrice().doubleValue());
        assertEquals(1, blockResponse.parks().size());

        ParkResponse parkResponse = blockResponse.parks().iterator().next();
        assertEquals(1L, parkResponse.id());
        assertEquals("Solar Park 1", parkResponse.name());
        assertEquals(ParkType.SOLAR, parkResponse.type());
        assertEquals(50.0, parkResponse.capacity());
    }

    @Test
    public void testGetBlocks_NullInput() {
        // Arrange
        Set<ReadBlockEntity> readBlockEntities = null;

        // Act
        Set<BlockResponse> result = transformer.getBlocks(readBlockEntities);

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    public void testGetParks_Success() {
        // Arrange
        Set<ReadParkEntity> readParkEntities = Set.of(
                new ReadParkEntity(
                        1L,
                        "Solar Park 1",
                        ParkType.SOLAR,
                        50.0
                )
        );

        // Act
        Set<ParkResponse> result = transformer.getParks(readParkEntities);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());

        ParkResponse parkResponse = result.iterator().next();
        assertEquals(1L, parkResponse.id());
        assertEquals("Solar Park 1", parkResponse.name());
        assertEquals(ParkType.SOLAR, parkResponse.type());
        assertEquals(50.0, parkResponse.capacity());
    }

    @Test
    public void testGetParks_NullInput() {
        // Arrange
        Set<ReadParkEntity> readParkEntities = null;

        // Act
        Set<ParkResponse> result = transformer.getParks(readParkEntities);

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}
