package com.agregio.applicationservices.exceptions;

public class OfferNotFoundException extends RuntimeException {
    public OfferNotFoundException(Long offerId) {
        super("Offer not found with ID: " + offerId);
    }
}