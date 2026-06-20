package com.termux.app.sandbox;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.termux.shared.shell.command.ExecutionCommand;
import com.termux.shared.shell.command.ExecutionCommand.Runner;
import com.termux.shared.termux.TermuxConstants;
import com.termux.shared.termux.shell.TermuxShellManager;

/**
 * Mediates AI-proposed commands before they become Termux execution commands.
 *
 * The sandbox grants only Termux-level shell execution after policy approval. It does not request
 * or elevate Android app permissions, and it deliberately exposes no TerminalSession or process
 * handle back to AI code.
 */
public final class SandboxExecutor {

    public interface CommandDispatcher {
        void dispatch(@NonNull ExecutionCommand executionCommand);
    }

    @NonNull
    private final SandboxExecutionPolicy policy;
    @NonNull
    private final CommandDispatcher commandDispatcher;

    public SandboxExecutor(@NonNull CommandDispatcher commandDispatcher) {
        this(new SandboxExecutionPolicy(), commandDispatcher);
    }

    public SandboxExecutor(@NonNull SandboxExecutionPolicy policy,
                           @NonNull CommandDispatcher commandDispatcher) {
        this.policy = policy;
        this.commandDispatcher = commandDispatcher;
    }

    @NonNull
    public SandboxResult executeAiCommand(@NonNull String commandLine,
                                          @Nullable String workingDirectory,
                                          boolean userApprovedHighRisk) {
        SandboxExecutionPolicy.Decision decision = policy.evaluate(commandLine, userApprovedHighRisk);
        if (!decision.isAllowed())
            return SandboxResult.notExecuted(decision);

        ExecutionCommand executionCommand = buildSandboxedExecutionCommand(commandLine, workingDirectory);
        commandDispatcher.dispatch(executionCommand);
        return SandboxResult.executed(decision);
    }

    @NonNull
    private ExecutionCommand buildSandboxedExecutionCommand(@NonNull String commandLine,
                                                            @Nullable String workingDirectory) {
        ExecutionCommand executionCommand = new ExecutionCommand(TermuxShellManager.getNextShellId(),
            TermuxConstants.TERMUX_BIN_PREFIX_DIR_PATH + "/sh", new String[]{"-c", commandLine}, null, workingDirectory,
            Runner.TERMINAL_SESSION.getName(), false);
        executionCommand.commandLabel = "AI Sandbox Command";
        executionCommand.commandDescription = "Command proposed by AI and approved by sandbox policy";
        executionCommand.shellName = "AI Sandbox";
        return executionCommand;
    }

    public static final class SandboxResult {
        private final boolean executed;
        @NonNull
        private final SandboxExecutionPolicy.Decision decision;

        private SandboxResult(boolean executed, @NonNull SandboxExecutionPolicy.Decision decision) {
            this.executed = executed;
            this.decision = decision;
        }

        @NonNull
        public static SandboxResult executed(@NonNull SandboxExecutionPolicy.Decision decision) {
            return new SandboxResult(true, decision);
        }

        @NonNull
        public static SandboxResult notExecuted(@NonNull SandboxExecutionPolicy.Decision decision) {
            return new SandboxResult(false, decision);
        }

        public boolean isExecuted() { return executed; }
        @NonNull public SandboxExecutionPolicy.Decision getDecision() { return decision; }
    }
}
