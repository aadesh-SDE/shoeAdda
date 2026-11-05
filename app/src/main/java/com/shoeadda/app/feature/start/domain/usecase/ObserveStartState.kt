package com.shoeadda.app.feature.start.domain.usecase

import com.shoeadda.app.feature.start.domain.model.StartState
import com.shoeadda.app.feature.start.domain.repository.StartRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class ObserveStartState @Inject constructor(
    private val repository: StartRepository,
) {
    operator fun invoke(): Flow<StartState> = repository.startState
}
