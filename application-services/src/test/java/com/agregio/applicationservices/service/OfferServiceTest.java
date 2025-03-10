package com.agregio.applicationservices.service;

import com.agregio.adaptersoutput.jdbc.OfferEntityReadModel;
import com.agregio.adaptersoutput.jdbc.entities.ReadOfferEntities;
import com.agregio.adaptersoutput.jdbc.entities.ReadOfferEntity;
import com.agregio.applicationservices.conversion.OfferDtoToOffer;
import com.agregio.applicationservices.conversion.ReadOfferEntitiesToOfferResponseDetails;
import com.agregio.applicationservices.dto.OfferDto;
import com.agregio.applicationservices.dto.OfferResponseDetails;
import com.agregio.applicationservices.exceptions.ParkNotFoundException;
import com.agregio.domain.coredomain.*;
import com.agregio.domain.repository.OfferRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OfferServiceTest {

    @Mock
    private OfferRepository offerRepository;

    @Mock
    private OfferDtoToOffer offerDtoToOffer;

    @Mock
    private OfferEntityReadModel offerEntityReadModel;

    @Mock
    private ReadOfferEntitiesToOfferResponseDetails readOfferEntitiesToOfferResponseDetails;

    @Mock
    private ParkService parkService;

    @InjectMocks
    private OfferService offerService;

    @Test
    public void testCreateOffer_Success() {
        // Arrange
        var offerDto = mock(OfferDto.class);

        Park park = new Park(1l, null, null, 0);
        Block block = new Block(1l, LocalDateTime.now(), 3, 100, new BigDecimal(50.0), List.of(park));
        var offer = new Offer(1l, MarketType.RESERVE_PRIMAIRE, List.of(block));

        when(offerDtoToOffer.convert(offerDto)).thenReturn(offer);
        when(parkService.findById(anyLong())).thenReturn(Optional.of(new Park(1L, "Park 1", ParkType.SOLAR, 300000L)));
        when(offerRepository.createOffer(offer)).thenReturn(1L);

        // Act
        Long result = offerService.createOffer(offerDto);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result);
        verify(offerDtoToOffer, times(1)).convert(offerDto);
        verify(parkService, atLeastOnce()).findById(anyLong());
        verify(offerRepository, times(1)).createOffer(offer);
    }

    @Test
    public void testCreateOffer_ParkNotFound() {
        // Arrange
        var offerDto = mock(OfferDto.class);

        Park park = new Park(1l, null, null, 0);
        Block block = new Block(1l, LocalDateTime.now(), 3, 100, new BigDecimal(50.0), List.of(park));
        var offer = new Offer(1l, MarketType.RESERVE_PRIMAIRE, List.of(block));

        when(offerDtoToOffer.convert(offerDto)).thenReturn(offer);
        when(parkService.findById(anyLong())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ParkNotFoundException.class, () -> offerService.createOffer(offerDto));
        verify(offerDtoToOffer, times(1)).convert(offerDto);
        verify(parkService, atLeastOnce()).findById(anyLong());
        verify(offerRepository, never()).createOffer(offer);
    }

    @Test
    public void testGetOffersByMarket_Success() {
        // Arrange
        MarketType marketType = MarketType.RESERVE_PRIMAIRE;
        var readOfferEntity = new ReadOfferEntity(1l , marketType, Set.of());
        var readOfferEntities = new ReadOfferEntities(List.of(readOfferEntity));
        OfferResponseDetails offerResponseDetails = new OfferResponseDetails(1l , marketType, Set.of());

        when(offerEntityReadModel.findOffersWithBlocksWithParksByMarketType(marketType)).thenReturn(readOfferEntities);
        when(readOfferEntitiesToOfferResponseDetails.convert(readOfferEntity)).thenReturn(offerResponseDetails);

        // Act
        List<OfferResponseDetails> result = offerService.getOffersByMarket(marketType);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(offerResponseDetails, result.get(0));
        verify(offerEntityReadModel, times(1)).findOffersWithBlocksWithParksByMarketType(marketType);
        verify(readOfferEntitiesToOfferResponseDetails, times(1)).convert(readOfferEntity);
    }

    @Test
    public void testGetOffersByMarket_NoOffersFound() {
        // Arrange
        MarketType marketType = MarketType.RESERVE_PRIMAIRE;

        when(offerEntityReadModel.findOffersWithBlocksWithParksByMarketType(marketType)).thenReturn(null);

        // Act
        List<OfferResponseDetails> result = offerService.getOffersByMarket(marketType);

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(offerEntityReadModel, times(1)).findOffersWithBlocksWithParksByMarketType(marketType);
        verify(readOfferEntitiesToOfferResponseDetails, never()).convert(any());
    }
}
