package com.termux.app.packages;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/** Result callback for package installation requests. */
public interface PackageInstallerCallback {
    void onPackageInstalled(@NonNull String packageName);
    void onPackageInstallFailed(@NonNull String packageName, @Nullable String errorMessage);
}
