package com.termux.app.packages;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Objects;

/**
 * Immutable package metadata exposed by the package management layer.
 */
public final class PackageInfo {

    @NonNull
    private final String name;
    @NonNull
    private final String version;
    @NonNull
    private final String description;
    @NonNull
    private final PackageStatus status;

    public PackageInfo(@NonNull String name, @NonNull String version, @Nullable String description,
                       @NonNull PackageStatus status) {
        this.name = Objects.requireNonNull(name, "name");
        this.version = Objects.requireNonNull(version, "version");
        this.description = description == null ? "" : description;
        this.status = Objects.requireNonNull(status, "status");
    }

    @NonNull
    public String getName() {
        return name;
    }

    @NonNull
    public String getVersion() {
        return version;
    }

    @NonNull
    public String getDescription() {
        return description;
    }

    @NonNull
    public PackageStatus getStatus() {
        return status;
    }

    @NonNull
    public PackageInfo withStatus(@NonNull PackageStatus status) {
        return new PackageInfo(name, version, description, status);
    }
}
