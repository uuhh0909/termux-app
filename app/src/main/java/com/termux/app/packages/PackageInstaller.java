package com.termux.app.packages;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/** Boundary for optional package-management features. */
public interface PackageInstaller {

    /** Request installation of a package without exposing package-manager implementation details. */
    void installPackage(@NonNull String packageName, @Nullable PackageInstallerCallback callback);
}
