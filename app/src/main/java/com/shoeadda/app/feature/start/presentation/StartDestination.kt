package com.shoeadda.app.feature.start.presentation

import com.shoeadda.app.feature.start.domain.model.StartState

/** The first screen to show when the app opens. */
enum class StartDestination { Language, Intro, Home }

internal fun StartState.toDestination(): StartDestination = when {
    languageCode == null -> StartDestination.Language
    !onboardingCompleted -> StartDestination.Intro
    else -> StartDestination.Home
}
