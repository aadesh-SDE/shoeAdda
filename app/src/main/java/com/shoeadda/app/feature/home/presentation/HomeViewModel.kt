package com.shoeadda.app.feature.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shoeadda.app.feature.home.domain.model.HomeSections
import com.shoeadda.app.feature.home.domain.usecase.GetHomeSections
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

sealed interface HomeUiState {
    /** The catalog is still being loaded into the database (first start of the app). */
    data object Loading : HomeUiState

    data class Content(val sections: HomeSections) : HomeUiState
}

@HiltViewModel
class HomeViewModel @Inject constructor(
    getHomeSections: GetHomeSections,
) : ViewModel() {

    val uiState: StateFlow<HomeUiState> = getHomeSections()
        .map { sections -> if (sections.isEmpty) HomeUiState.Loading else HomeUiState.Content(sections) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(STOP_TIMEOUT_MS), HomeUiState.Loading)

    private companion object {
        const val STOP_TIMEOUT_MS = 5_000L
    }
}
