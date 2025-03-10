package com.agregio.adaptersinput;

import com.agregio.adaptersinput.controller.OfferController;
import com.agregio.applicationservices.service.OfferService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.mockito.Mockito.mock;

@Configuration
public class TestConfig {

    @Bean
    public OfferService offerService() {
        return mock(OfferService.class); // Mock the service
    }

    @Bean
    public OfferController offerController(OfferService offerService) {
        return new OfferController(offerService); // Provide the controller with the mocked service
    }
}
