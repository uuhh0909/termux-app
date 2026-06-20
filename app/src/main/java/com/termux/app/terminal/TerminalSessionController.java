package com.termux.app.terminal;

import androidx.annotation.Nullable;

import com.termux.shared.shell.command.ExecutionCommand;
import com.termux.shared.termux.shell.command.runner.terminal.TermuxSession;
import com.termux.terminal.TerminalSession;

/**
 * Boundary for the terminal-core layer.
 *
 * <p>Implementations own PTY-backed terminal session lifecycle, shell environment setup and
 * command execution. UI and optional features should depend on this interface instead of directly
 * manipulating TermuxService internals.</p>
 */
public interface TerminalSessionController {

    /** Create a foreground terminal session from an already-normalized execution command. */
    @Nullable
    TermuxSession createTerminalSession(@Nullable ExecutionCommand executionCommand);

    /** Create a foreground terminal session from explicit launch parameters. */
    @Nullable
    TermuxSession createTerminalSession(String executablePath, String[] arguments, String stdin,
                                        String workingDirectory, boolean isFailSafe, String sessionName);

    /** Finish and remove a terminal session, returning its previous index or {@code -1}. */
    int removeTerminalSession(TerminalSession sessionToRemove);
}
