package com.shoeadda.app.feature.start.domain.usecase

import com.shoeadda.app.feature.start.domain.repository.StartRepository
import javax.inject.Inject

class CompleteOnboarding @Inject constructor(
    private val repository: StartRepository,
) {
    suspend operator fun invoke() = repository.completeOnboarding()
}
