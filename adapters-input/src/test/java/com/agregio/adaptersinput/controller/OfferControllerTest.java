package com.agregio.adaptersinput.controller;

import com.agregio.adaptersinput.TestConfig;
import com.agregio.applicationservices.dto.OfferResponseDetails;
import com.agregio.applicationservices.service.OfferService;
import com.agregio.domain.coredomain.MarketType;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


import java.util.List;
import java.util.Set;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OfferController.class)
@ContextConfiguration(classes = TestConfig.class)
public class OfferControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private OfferService offerService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(new OfferController(offerService)).build();
    }

    @Test
    public void testGetOffersByMarket_Success() throws Exception {
        MarketType marketType = MarketType.RESERVE_PRIMAIRE;
        OfferResponseDetails offerResponseDetails = new OfferResponseDetails(
                1L,
                marketType,
                Set.of()
        );

        when(offerService.getOffersByMarket(marketType)).thenReturn(List.of(offerResponseDetails));

        mockMvc.perform(get("/api/v1/offers/{marketType}", marketType))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].marketType").value(marketType.name()));
    }

    @Test
    public void testGetOffersByMarket_NoOffersFound() throws Exception {
        MarketType marketType = MarketType.RESERVE_PRIMAIRE;

        when(offerService.getOffersByMarket(marketType)).thenReturn(List.of());

        mockMvc.perform(get("/api/v1/offers/{marketType}", marketType))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    public void testGetOffersByMarket_InvalidMarketType() throws Exception {
        String invalidMarketType = "INVALID_MARKET";

        mockMvc.perform(get("/api/v1/offers/{marketType}", invalidMarketType))
                .andExpect(status().isBadRequest());
    }
}
