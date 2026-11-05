package com.shoeadda.app.feature.start.data.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.shoeadda.app.feature.start.domain.model.StartState
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

private object StartKeys {
    val LanguageCode = stringPreferencesKey("language_code")
    val OnboardingCompleted = booleanPreferencesKey("onboarding_completed")
}

@Singleton
class StartPreferencesDataSource @Inject constructor(
    private val dataStore: DataStore<Preferences>,
) {
    val startState: Flow<StartState> = dataStore.data
        .catch { error ->
            // An unreadable file is treated as a first launch. Other errors are real bugs.
            if (error is IOException) emit(emptyPreferences()) else throw error
        }
        .map { it.toStartState() }

    suspend fun setLanguage(languageCode: String) {
        dataStore.edit { it[StartKeys.LanguageCode] = languageCode }
    }

    suspend fun setOnboardingCompleted() {
        dataStore.edit { it[StartKeys.OnboardingCompleted] = true }
    }
}

private fun Preferences.toStartState() = StartState(
    languageCode = this[StartKeys.LanguageCode],
    onboardingCompleted = this[StartKeys.OnboardingCompleted] ?: false,
)
