package com.termux.app.ui;

import androidx.annotation.Nullable;

import com.termux.terminal.TerminalSession;

/** Boundary for simplified UI coordination around terminal sessions. */
public interface TerminalUiController {

    /** Display the supplied terminal session in the foreground UI. */
    void showTerminalSession(@Nullable TerminalSession terminalSession);

    /** Notify the UI that the terminal session list changed. */
    void notifyTerminalSessionsChanged();
}
