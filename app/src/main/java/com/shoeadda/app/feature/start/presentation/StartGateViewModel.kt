package com.shoeadda.app.feature.start.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shoeadda.app.feature.start.domain.usecase.ObserveStartState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn

/**
 * Decides which screen the app opens on. It reads the saved state once. While it is null the system splash stays visible.
 * Later changes (the user picking a language, finishing the intro) are handled by navigation, not by this value.
 */
@HiltViewModel
class StartGateViewModel @Inject constructor(
    observeStartState: ObserveStartState,
) : ViewModel() {

    val initialDestination: StateFlow<StartDestination?> = flow {
        emit(observeStartState().first().toDestination())
    }.stateIn(viewModelScope, SharingStarted.Eagerly, null)
}
