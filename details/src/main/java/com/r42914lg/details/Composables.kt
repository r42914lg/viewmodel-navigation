package com.r42914lg.details

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun DetailsScreen(
    pictureViewModel: PictureViewModel = viewModel(),
) {
    val state by pictureViewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(pictureViewModel) {
        pictureViewModel.activate()
    }
    BackHandler {
        pictureViewModel.onBack()
    }
    Column {
        when (state) {
            is PictureViewState.Content -> {
                val s = state as PictureViewState.Content
                Text(text = "Showing details for ID -> ${s.breedId} \n ${s.message} ${s.pictureUrl}")
            }
            PictureViewState.Loading -> {
                Text(text = "Loading")
            }
        }
    }
}
