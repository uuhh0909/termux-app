package com.termux.app.ai;

import androidx.annotation.NonNull;

import java.util.List;

/**
 * Boundary for AI assistance. Implementations may explain a user request and suggest shell
 * commands, but must not execute commands or hold direct references to Android permissions,
 * TermuxService, or TerminalSession objects.
 */
public interface AiAssistantService {

    /**
     * Convert a text request into an AI response containing explanations and optional command
     * suggestions. Suggested commands are data only; callers must route them through the sandbox
     * execution policy before starting any shell process.
     */
    @NonNull
    AiAssistantResponse request(@NonNull String textRequest);

    final class AiAssistantResponse {
        @NonNull
        private final String explanation;
        @NonNull
        private final List<CommandSuggestion> commandSuggestions;

        public AiAssistantResponse(@NonNull String explanation,
                                   @NonNull List<CommandSuggestion> commandSuggestions) {
            this.explanation = explanation;
            this.commandSuggestions = commandSuggestions;
        }

        @NonNull
        public String getExplanation() {
            return explanation;
        }

        @NonNull
        public List<CommandSuggestion> getCommandSuggestions() {
            return commandSuggestions;
        }
    }

    final class CommandSuggestion {
        @NonNull
        private final String commandLine;
        @NonNull
        private final String explanation;

        public CommandSuggestion(@NonNull String commandLine, @NonNull String explanation) {
            this.commandLine = commandLine;
            this.explanation = explanation;
        }

        @NonNull
        public String getCommandLine() {
            return commandLine;
        }

        @NonNull
        public String getExplanation() {
            return explanation;
        }
    }
}
