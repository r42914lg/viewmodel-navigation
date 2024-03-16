package com.r42914lg.list

import androidx.lifecycle.ViewModel
import com.r42914lg.details.DetailsPageRoute
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class BreedViewModel(
    private val routeNavigator: com.r42914lg.navigation.RouteNavigator,
) : ViewModel(), com.r42914lg.navigation.RouteNavigator by routeNavigator {

    private val mutableBreedState: MutableStateFlow<BreedViewState> =
        MutableStateFlow(BreedViewState.Initial)

    val breedState: StateFlow<BreedViewState> = mutableBreedState.asStateFlow()

    fun activate() {
        observeBreeds()
    }

    fun onToDetailScreen(breed: String) {
        navigateToRoute(DetailsPageRoute.getRoute(breed))
    }

    fun onBack() {
        navigateUp()
    }

    private fun observeBreeds() {
        mutableBreedState.tryEmit(
            BreedViewState.Content(
                listOf("Breed-1", "Breed-2", "Breed-3", "Breed-4", "Breed-5"),
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
