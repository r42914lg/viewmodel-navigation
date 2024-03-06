package com.r42914lg.vmnav.models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PictureViewModel : ViewModel() {

    private val _state: MutableStateFlow<PictureViewState> = MutableStateFlow(PictureViewState.Loading)
    val state: StateFlow<PictureViewState> = _state.asStateFlow()

    suspend fun activate() {
        viewModelScope.launch {
            delay(2000)
            _state.tryEmit(PictureViewState.Content("pic-pic-test"))
        }
    }
}

sealed class PictureViewState {
    data object Loading : PictureViewState()
    data class Content (val pictureUrl: String) : PictureViewState()
}
