package com.termux.app.packages;

import androidx.annotation.NonNull;

import java.util.List;
import java.util.Objects;

/**
 * UI-independent facade for package management operations.
 *
 * <p>This service is intentionally not wired to a button or Activity. It can be reused by the
 * current UI, an AI action layer, or an internal CLI once a concrete package source is chosen.</p>
 */
public final class PackageManagementService {

    @NonNull
    private final PackageRepository repository;
    @NonNull
    private final PackageInstaller installer;

    public PackageManagementService(@NonNull PackageRepository repository, @NonNull PackageInstaller installer) {
        this.repository = Objects.requireNonNull(repository, "repository");
        this.installer = Objects.requireNonNull(installer, "installer");
    }

    @NonNull
    public List<PackageInfo> search(@NonNull String query) throws PackageOperationException {
        return repository.search(query);
    }

    @NonNull
    public PackageInfo install(@NonNull String packageName) throws PackageOperationException {
        return installer.install(packageName);
    }

    @NonNull
    public PackageInfo uninstall(@NonNull String packageName) throws PackageOperationException {
        return installer.uninstall(packageName);
    }

    @NonNull
    public PackageInfo update(@NonNull String packageName) throws PackageOperationException {
        return installer.update(packageName);
    }

    @NonNull
    public List<PackageInfo> listInstalled() throws PackageOperationException {
        return repository.listInstalled();
    }
}
