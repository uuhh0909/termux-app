package com.termux.app.packages;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

/**
 * Source of package metadata for package-management clients.
 *
 * <p>Implementations may later read from Termux repositories, a custom package index,
 * local cache, or any other package source without changing UI, AI, or CLI callers.</p>
 */
public interface PackageRepository {

    @NonNull
    List<PackageInfo> search(@NonNull String query) throws PackageOperationException;

    @NonNull
    List<PackageInfo> listInstalled() throws PackageOperationException;

    @Nullable
    PackageInfo getPackage(@NonNull String packageName) throws PackageOperationException;
}
