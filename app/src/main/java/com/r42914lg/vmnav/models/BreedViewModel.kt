package com.r42914lg.vmnav.models

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class BreedViewModel : ViewModel() {

    private val mutableBreedState: MutableStateFlow<BreedViewState> =
        MutableStateFlow(BreedViewState.Initial)

    val breedState: StateFlow<BreedViewState> = mutableBreedState.asStateFlow()

    suspend fun activate() {
        observeBreeds()
    }

    private suspend fun observeBreeds() {
        mutableBreedState.tryEmit(
            BreedViewState.Content(
                listOf("dsfsf, afsdfdsf, dsfsdfsdf, eqiwueyuwqiye, badhjagdja"),
                false
            )
        )
    }
}

sealed class BreedViewState {
    abstract val isLoading: Boolean

    data object Initial : BreedViewState() {
        override val isLoading: Boolean = true
    }

    data class Content (
        val breeds: List<String>,
        override val isLoading: Boolean = false
    ) : BreedViewState()
}
