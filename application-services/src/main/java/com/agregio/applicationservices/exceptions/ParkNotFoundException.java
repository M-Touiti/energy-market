package com.agregio.applicationservices.exceptions;

public class ParkNotFoundException extends RuntimeException {
    public ParkNotFoundException(Long parkId) {
        super("Park not found with ID: " + parkId);
    }
}
