package com.fredjo.DevRegistery.utils;

/**
 * Exception thrown when a developer is not found.
 */
public class DeveloperNotFoundException extends RuntimeException {
    public DeveloperNotFoundException(String message) {
        super(message);
    }
}
