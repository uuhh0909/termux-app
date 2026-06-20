package com.termux.app.packages;

/**
 * Checked exception for package repository and installer operations.
 */
public class PackageOperationException extends Exception {

    public PackageOperationException(String message) {
        super(message);
    }

    public PackageOperationException(String message, Throwable cause) {
        super(message, cause);
    }
}
