package com.shoeadda.app.feature.start.domain.repository

import com.shoeadda.app.feature.start.domain.model.StartState
import kotlinx.coroutines.flow.Flow

interface StartRepository {
    val startState: Flow<StartState>

    /** Saves the language and switches the app to it. */
    suspend fun setLanguage(languageCode: String)

    suspend fun completeOnboarding()
}
