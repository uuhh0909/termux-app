package com.termux.app.sandbox;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Boundary for sandboxed or high-risk work.
 *
 * <p>Future implementations can route commands through permission checks, isolated processes or
 * user-confirmed execution policies without coupling callers to Android service details.</p>
 */
public interface SandboxExecutor {

    /** Execute a task after applying sandbox policy. */
    void execute(@NonNull SandboxTask task, @Nullable SandboxExecutionCallback callback);
}
