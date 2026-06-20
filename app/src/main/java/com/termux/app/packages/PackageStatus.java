package com.termux.app.packages;

/**
 * Installation state for a package known by the package management layer.
 */
public enum PackageStatus {
    AVAILABLE,
    INSTALLED,
    UPDATE_AVAILABLE,
    INSTALLING,
    UNINSTALLING,
    UPDATING,
    ERROR,
    UNKNOWN
}
