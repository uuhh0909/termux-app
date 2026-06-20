package com.termux.app.ai;

import androidx.annotation.NonNull;

/** Boundary reserved for future AI-assisted features. */
public interface AiFeatureController {

    /** Return whether AI features are available in the current build and user configuration. */
    boolean isAiAvailable();

    /** Handle a user prompt without exposing AI provider details to terminal or UI layers. */
    void handlePrompt(@NonNull String prompt);
}
