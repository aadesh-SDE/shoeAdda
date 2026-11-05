package com.shoeadda.app.feature.start.data.repository

import com.shoeadda.app.feature.start.data.local.StartPreferencesDataSource
import com.shoeadda.app.feature.start.domain.model.StartState
import com.shoeadda.app.feature.start.domain.repository.LocaleController
import com.shoeadda.app.feature.start.domain.repository.StartRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class StartRepositoryImpl @Inject constructor(
    private val dataSource: StartPreferencesDataSource,
    private val localeController: LocaleController,
) : StartRepository {

    override val startState: Flow<StartState> = dataSource.startState

    override suspend fun setLanguage(languageCode: String) {
        // Save first: switching language recreates the screen.
        dataSource.setLanguage(languageCode)
        localeController.applyLanguage(languageCode)
    }

    override suspend fun completeOnboarding() = dataSource.setOnboardingCompleted()
}
