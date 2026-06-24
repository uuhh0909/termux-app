package com.termux.app.sandbox;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/** Immutable description of work that must cross the sandbox boundary. */
public final class SandboxTask {

    @NonNull
    private final String mCommand;
    @Nullable
    private final String[] mArguments;
    private final boolean mRequiresUserConfirmation;

    public SandboxTask(@NonNull String command, @Nullable String[] arguments, boolean requiresUserConfirmation) {
        mCommand = command;
        mArguments = arguments;
        mRequiresUserConfirmation = requiresUserConfirmation;
    }

    @NonNull
    public String getCommand() { return mCommand; }

    @Nullable
    public String[] getArguments() { return mArguments; }

    public boolean requiresUserConfirmation() { return mRequiresUserConfirmation; }
}
