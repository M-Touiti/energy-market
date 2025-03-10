package com.agregio.applicationservices.conversion;

import com.agregio.applicationservices.dto.BlockDto;
import com.agregio.applicationservices.dto.OfferDto;
import com.agregio.applicationservices.dto.ParkDto;
import com.agregio.domain.coredomain.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.security.InvalidParameterException;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OfferDtoToOfferTest {

    @InjectMocks
    private OfferDtoToOffer transformer;

    @Test
    public void testConvertOfferDtoToOffer_Success() {
        // Arrange
        OfferDto offerDto = new OfferDto(
                1L,
                MarketType.RESERVE_PRIMAIRE,
                List.of(new BlockDto(
                        1L,
                        LocalDateTime.of(2025, 03, 10, 8, 0),
                        3,
                        100.0,
                        new BigDecimal(50.0),
                        List.of(new ParkDto(
                                1L,
                                "Solar Park 1",
                                ParkType.SOLAR,
                                50.0
                        ))
                ))
        );

        // Act
        Offer result = transformer.convert(offerDto);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.id());
        assertEquals(MarketType.RESERVE_PRIMAIRE, result.marketType());
        assertEquals(1, result.blocks().size());

        Block block = result.blocks().get(0);
        assertEquals(1L, block.id());
        assertEquals(LocalDateTime.of(2025, 03, 10, 8, 0), block.startTime());
        assertEquals(3, block.durationHours());
        assertEquals(100.0, block.quantity());
        assertEquals(50.0, block.floorPrice().doubleValue());
        assertEquals(1, block.parks().size());

        Park park = block.parks().get(0);
        assertEquals(1L, park.id());
        assertEquals("Solar Park 1", park.name());
        assertEquals(ParkType.SOLAR, park.type());
        assertEquals(50.0, park.capacity());
    }

    @Test
    public void testConvertOfferDtoToOffer_NullInput() {
        // Arrange
        OfferDto offerDto = null;

        // Act & Assert
        InvalidParameterException exception = assertThrows(InvalidParameterException.class, () -> transformer.convert(offerDto));
        assertEquals("Field parameter cannot be null", exception.getMessage());
    }

    @Test
    public void testConvertToBlocks_Success() {
        // Arrange
        List<BlockDto> blockDtos = List.of(
                new BlockDto(
                        1L,
                        LocalDateTime.of(2025, 03, 10, 8, 0),
                        3,
                        100.0,
                        new BigDecimal(50.0),
                        List.of(new ParkDto(
                                1L,
                                "Solar Park 1",
                                ParkType.SOLAR,
                                50.0
                        ))
                )
        );

        // Act
        List<Block> result = transformer.convertToBlocks(blockDtos);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());

        Block block = result.get(0);
        assertEquals(1L, block.id());
        assertEquals(LocalDateTime.of(2025, 03, 10, 8, 0), block.startTime());
        assertEquals(3, block.durationHours());
        assertEquals(100.0, block.quantity());
        assertEquals(50.0, block.floorPrice().doubleValue());
        assertEquals(1, block.parks().size());

        Park park = block.parks().get(0);
        assertEquals(1L, park.id());
        assertEquals("Solar Park 1", park.name());
        assertEquals(ParkType.SOLAR, park.type());
        assertEquals(50.0, park.capacity());
    }

    @Test
    public void testConvertToBlocks_NullInput() {
        // Arrange
        List<BlockDto> blockDtos = null;

        // Act
        List<Block> result = transformer.convertToBlocks(blockDtos);

        // Assert
        assertNull(result);
    }

    @Test
    public void testConvertBlockDtoToBlock_Success() {
        // Arrange
        BlockDto blockDto = new BlockDto(
                1L,
                LocalDateTime.of(2025, 03, 10, 8, 0),
                3,
                100.0,
                new BigDecimal(50.0),
                List.of(new ParkDto(
                        1L,
                        "Solar Park 1",
                        ParkType.SOLAR,
                        50.0
                ))
        );

        // Act
        Block result = transformer.convert(blockDto);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.id());
        assertEquals(LocalDateTime.of(2025, 03, 10, 8, 0), result.startTime());
        assertEquals(3, result.durationHours());
        assertEquals(100.0, result.quantity());
        assertEquals(50.0, result.floorPrice().doubleValue());
        assertEquals(1, result.parks().size());

        Park park = result.parks().get(0);
        assertEquals(1L, park.id());
        assertEquals("Solar Park 1", park.name());
        assertEquals(ParkType.SOLAR, park.type());
        assertEquals(50.0, park.capacity());
    }

    @Test
    public void testConvertBlockDtoToBlock_NullInput() {
        // Arrange
        BlockDto blockDto = null;

        // Act
        Block result = transformer.convert(blockDto);

        // Assert
        assertNull(result);
    }

    @Test
    public void testConvertToParks_Success() {
        // Arrange
        List<ParkDto> parkDtos = List.of(
                new ParkDto(
                        1L,
                        "Solar Park 1",
                        ParkType.SOLAR,
                        50.0
                )
        );

        // Act
        List<Park> result = transformer.convertToParks(parkDtos);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());

        Park park = result.get(0);
        assertEquals(1L, park.id());
        assertEquals("Solar Park 1", park.name());
        assertEquals(ParkType.SOLAR, park.type());
        assertEquals(50.0, park.capacity());
    }

    @Test
    public void testConvertToParks_NullInput() {
        // Arrange
        List<ParkDto> parkDtos = null;

        // Act
        List<Park> result = transformer.convertToParks(parkDtos);

        // Assert
        assertNull(result);
    }

    @Test
    public void testConvertParkDtoToPark_Success() {
        // Arrange
        ParkDto parkDto = new ParkDto(
                1L,
                "Solar Park 1",
                ParkType.SOLAR,
                50.0
        );

        // Act
        Park result = transformer.convert(parkDto);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.id());
        assertEquals("Solar Park 1", result.name());
        assertEquals(ParkType.SOLAR, result.type());
        assertEquals(50.0, result.capacity());
    }

    @Test
    public void testConvertParkDtoToPark_NullInput() {
        // Arrange
        ParkDto parkDto = null;

        // Act
        Park result = transformer.convert(parkDto);

        // Assert
        assertNull(result);
    }

    @Test
    public void testConvertParkToParkDto_Success() {
        // Arrange
        Park park = new Park(
                1L,
                "Solar Park 1",
                ParkType.SOLAR,
                50.0
        );

        // Act
        ParkDto result = transformer.convert(park);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Solar Park 1", result.getName());
        assertEquals(ParkType.SOLAR, result.getType());
        assertEquals(50.0, result.getCapacity());
    }

    @Test
    public void testConvertParkToParkDto_NullInput() {
        // Arrange
        Park park = null;

        // Act
        ParkDto result = transformer.convert(park);

        // Assert
        assertNull(result);
    }
}
