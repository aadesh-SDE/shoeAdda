package com.shoeadda.app.feature.start.presentation.language

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shoeadda.app.feature.start.domain.usecase.GetDefaultLanguageCode
import com.shoeadda.app.feature.start.domain.usecase.GetSupportedLanguages
import com.shoeadda.app.feature.start.domain.usecase.ObserveStartState
import com.shoeadda.app.feature.start.domain.usecase.SetAppLanguage
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class LanguageViewModel @Inject constructor(
    getSupportedLanguages: GetSupportedLanguages,
    private val observeStartState: ObserveStartState,
    private val getDefaultLanguageCode: GetDefaultLanguageCode,
    private val setAppLanguage: SetAppLanguage,
) : ViewModel() {

    private val _uiState = MutableStateFlow(LanguageUiState(languages = getSupportedLanguages()))
    val uiState: StateFlow<LanguageUiState> = _uiState.asStateFlow()

    private val _events = Channel<LanguageEvent>(Channel.BUFFERED)
    val events: Flow<LanguageEvent> = _events.receiveAsFlow()

    init {
        viewModelScope.launch {
            val saved = observeStartState().first().languageCode
            _uiState.update { it.copy(selectedCode = saved ?: getDefaultLanguageCode()) }
        }
    }

    /** Selecting a language switches the app to it straight away, so the user sees the result. */
    fun onLanguageSelected(code: String) {
        if (code == _uiState.value.selectedCode) return
        _uiState.update { it.copy(selectedCode = code) }
        viewModelScope.launch { setAppLanguage(code) }
    }

    fun onContinue() {
        val code = _uiState.value.selectedCode
        if (code.isEmpty()) return
        viewModelScope.launch {
            // Also saves the phone's language when the user did not tap any row.
            setAppLanguage(code)
            _events.send(LanguageEvent.Continue)
        }
    }
}
