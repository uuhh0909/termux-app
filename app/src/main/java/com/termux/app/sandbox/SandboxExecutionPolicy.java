package com.termux.app.sandbox;

import androidx.annotation.NonNull;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.regex.Pattern;

/** Policy gate for commands proposed by AI before they can reach Termux shell execution. */
public final class SandboxExecutionPolicy {

    private static final Set<String> SAFE_COMMAND_ALLOWLIST = Collections.unmodifiableSet(new HashSet<>(Arrays.asList(
        "awk", "basename", "cat", "cd", "clear", "cut", "date", "df", "du", "echo", "env", "find", "grep",
        "head", "less", "ls", "man", "pwd", "sed", "sort", "tail", "tar", "tee", "tree", "uname", "wc", "which"
    )));

    private static final Set<String> HIGH_RISK_COMMANDS = Collections.unmodifiableSet(new HashSet<>(Arrays.asList(
        "chmod", "chown", "curl", "dd", "ftp", "git", "iptables", "mount", "mv", "nc", "ncat", "netcat",
        "pkg", "python", "python3", "reboot", "rm", "rsync", "scp", "ssh", "su", "sudo", "termux-setup-storage",
        "umount", "wget"
    )));

    private static final Pattern WIDE_DELETE_PATTERN = Pattern.compile("(^|[;&|\\s])rm\\s+[^\n]*(?:-[^\n]*r|--recursive)[^\n]*(?:\\s/|\\s~|\\s\\*|/\\*)");
    private static final Pattern ANDROID_SYSTEM_PATH_PATTERN = Pattern.compile("(^|[;&|\\s])(?:chmod|chown|dd|mount|mv|rm|sed|tee)\\b[^\n]*(?:\\s/(?:system|vendor|odm|product|apex|proc|sys|dev)(?:/|\\s|$))");
    private static final Pattern SENSITIVE_NETWORK_PATTERN = Pattern.compile("(^|[;&|\\s])(?:nc|ncat|netcat|ssh|scp|ftp|curl|wget)\\b");

    @NonNull
    public Decision evaluate(@NonNull String commandLine, boolean userApprovedHighRisk) {
        String normalized = commandLine.trim();
        if (normalized.isEmpty())
            return Decision.blocked("Empty AI command suggestion.");

        String lower = normalized.toLowerCase(Locale.ROOT);
        if (WIDE_DELETE_PATTERN.matcher(lower).find())
            return Decision.blocked("Wide recursive deletion is blocked for AI commands.");
        if (ANDROID_SYSTEM_PATH_PATTERN.matcher(lower).find())
            return Decision.blocked("AI commands may not modify Android system paths.");
        if (SENSITIVE_NETWORK_PATTERN.matcher(lower).find() && !userApprovedHighRisk)
            return Decision.requiresApproval("Sensitive network commands require explicit user approval.");

        String commandName = firstCommandName(lower);
        if (HIGH_RISK_COMMANDS.contains(commandName) && !userApprovedHighRisk)
            return Decision.requiresApproval("High-risk command '" + commandName + "' requires explicit user approval.");
        if (SAFE_COMMAND_ALLOWLIST.contains(commandName))
            return Decision.allowed("Allowed sandbox command.");

        return userApprovedHighRisk
            ? Decision.allowed("User approved non-allowlisted sandbox command.")
            : Decision.requiresApproval("Command is not in the AI sandbox allowlist.");
    }

    @NonNull
    private static String firstCommandName(@NonNull String commandLine) {
        String[] parts = commandLine.split("\\s+", 2);
        String command = parts.length > 0 ? parts[0] : "";
        int slashIndex = command.lastIndexOf('/');
        return slashIndex >= 0 ? command.substring(slashIndex + 1) : command;
    }

    public static final class Decision {
        public enum Status { ALLOWED, REQUIRES_APPROVAL, BLOCKED }
        @NonNull private final Status status;
        @NonNull private final String reason;
        private Decision(@NonNull Status status, @NonNull String reason) { this.status = status; this.reason = reason; }
        @NonNull public static Decision allowed(@NonNull String reason) { return new Decision(Status.ALLOWED, reason); }
        @NonNull public static Decision requiresApproval(@NonNull String reason) { return new Decision(Status.REQUIRES_APPROVAL, reason); }
        @NonNull public static Decision blocked(@NonNull String reason) { return new Decision(Status.BLOCKED, reason); }
        @NonNull public Status getStatus() { return status; }
        @NonNull public String getReason() { return reason; }
        public boolean isAllowed() { return status == Status.ALLOWED; }
    }
}
