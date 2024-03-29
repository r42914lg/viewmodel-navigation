package com.r42914lg.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.r42914lg.navigation.RouteNavigator
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PictureViewModel(
    private val breedId: String,
    private val message: String,
    private val routeNavigator: RouteNavigator,
) : ViewModel(), RouteNavigator by routeNavigator {

    private val _state: MutableStateFlow<PictureViewState> = MutableStateFlow(PictureViewState.Loading)
    val state: StateFlow<PictureViewState> = _state.asStateFlow()

    suspend fun activate() {
        viewModelScope.launch {
            delay(2000)
            _state.tryEmit(PictureViewState.Content("some-pic-url", breedId, message))
        }
    }

    fun onBack() {
        navigateUp()
    }
}

sealed class PictureViewState {
    data object Loading : PictureViewState()
    data class Content (
        val pictureUrl: String,
        val breedId: String,
        val message: String,
    ) : PictureViewState()
}
