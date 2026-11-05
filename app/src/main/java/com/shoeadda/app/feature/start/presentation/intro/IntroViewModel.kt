package com.shoeadda.app.feature.start.presentation.intro

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shoeadda.app.feature.start.domain.usecase.CompleteOnboarding
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

sealed interface IntroEvent {
    /** The intro is marked as done. Go to the app. */
    data object Finished : IntroEvent
}

@HiltViewModel
class IntroViewModel @Inject constructor(
    private val completeOnboarding: CompleteOnboarding,
) : ViewModel() {

    private val _events = Channel<IntroEvent>(Channel.BUFFERED)
    val events: Flow<IntroEvent> = _events.receiveAsFlow()

    /** Called by both Skip and Get started. */
    fun onFinish() {
        viewModelScope.launch {
            completeOnboarding()
            _events.send(IntroEvent.Finished)
        }
    }
}
