package com.termux.app.sandbox;

import androidx.annotation.Nullable;

/** Result callback for sandboxed execution requests. */
public interface SandboxExecutionCallback {
    void onCompleted(int exitCode, @Nullable String message);
}
