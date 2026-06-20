package com.termux.app.packages;

import androidx.annotation.NonNull;

/**
 * Performs package lifecycle operations independently of the current app UI.
 */
public interface PackageInstaller {

    @NonNull
    PackageInfo install(@NonNull String packageName) throws PackageOperationException;

    @NonNull
    PackageInfo uninstall(@NonNull String packageName) throws PackageOperationException;

    @NonNull
    PackageInfo update(@NonNull String packageName) throws PackageOperationException;
}
