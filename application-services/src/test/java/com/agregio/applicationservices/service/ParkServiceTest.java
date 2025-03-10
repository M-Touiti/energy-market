package com.agregio.applicationservices.service;

import com.agregio.adaptersoutput.jdbc.ParkEntityReadModel;
import com.agregio.applicationservices.conversion.OfferDtoToOffer;
import com.agregio.applicationservices.dto.ParkDto;
import com.agregio.domain.coredomain.MarketType;
import com.agregio.domain.coredomain.Park;
import com.agregio.domain.repository.ParkRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ParkServiceTest {

    @Mock
    private OfferDtoToOffer offerDtoToOffer;

    @Mock
    private ParkRepository parkRepository;

    @Mock
    private ParkEntityReadModel parkEntityReadModel;

    @InjectMocks
    private ParkService parkService;

    @Test
    public void testCreatePark_Success() {
        // Arrange
        var parkDto = mock(ParkDto.class);
        var park = mock(Park.class);

        when(offerDtoToOffer.convert(parkDto)).thenReturn(park);
        when(parkRepository.createPark(park)).thenReturn(1L);

        // Act
        Long result = parkService.createPark(parkDto);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result);
        verify(offerDtoToOffer, times(1)).convert(parkDto);
        verify(parkRepository, times(1)).createPark(park);
    }

    @Test
    public void testGetParksByMarket_Success() {
        // Arrange
        MarketType marketType = MarketType.RESERVE_PRIMAIRE;
        var parkDto = mock(ParkDto.class);
        var park = mock(Park.class);

        when(parkEntityReadModel.getParksByMarket(marketType)).thenReturn(List.of(park));
        when(offerDtoToOffer.convert(park)).thenReturn(parkDto);

        // Act
        List<ParkDto> result = parkService.getParksByMarket(marketType);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(parkDto, result.get(0));
        verify(parkEntityReadModel, times(1)).getParksByMarket(marketType);
        verify(offerDtoToOffer, times(1)).convert(park);
    }

    @Test
    public void testGetParksByMarket_NoParksFound() {
        // Arrange
        MarketType marketType = MarketType.RESERVE_PRIMAIRE;

        when(parkEntityReadModel.getParksByMarket(marketType)).thenReturn(null);

        // Act
        List<ParkDto> result = parkService.getParksByMarket(marketType);

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(parkEntityReadModel, times(1)).getParksByMarket(marketType);
        verify(offerDtoToOffer, never()).convert(any(ParkDto.class));
    }

    @Test
    public void testFindById_Success() {
        // Arrange
        Long parkId = 1L;
        var park = mock(Park.class);

        when(parkRepository.findById(parkId)).thenReturn(Optional.of(park));

        // Act
        Optional<Park> result = parkService.findById(parkId);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(park, result.get());
        verify(parkRepository, times(1)).findById(parkId);
    }

    @Test
    public void testFindById_NotFound() {
        // Arrange
        Long parkId = 1L;

        when(parkRepository.findById(parkId)).thenReturn(Optional.empty());

        // Act
        Optional<Park> result = parkService.findById(parkId);

        // Assert
        assertFalse(result.isPresent());
        verify(parkRepository, times(1)).findById(parkId);
    }
}
