package com.r42914lg.vmnav.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Divider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.items
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.r42914lg.vmnav.models.BreedViewModel
import com.r42914lg.vmnav.models.BreedViewState
import com.r42914lg.vmnav.models.PictureViewModel
import com.r42914lg.vmnav.models.PictureViewState

@Composable
fun MainScreen(
    viewModel: BreedViewModel = viewModel(),
    onToDetails: (String) -> Unit,
    onBack: () -> Unit,
) {
    val dogsState by viewModel.breedState.collectAsStateWithLifecycle()
    val scope = rememberCoroutineScope()

    LaunchedEffect(viewModel) {
        viewModel.activate()
    }
    BackHandler {
        onBack()
    }
    MainScreenContent(
        dogsState = dogsState,
        onBreedSelected = onToDetails
    )
}

@Composable
fun MainScreenContent(
    dogsState: BreedViewState,
    onBreedSelected: (String) -> Unit,
) {
    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        Box {
            when (dogsState) {
                is BreedViewState.Content -> {
                    val breeds = dogsState.breeds
                    Success(
                        successData = breeds,
                        drillDown = onBreedSelected,
                    )
                }
                BreedViewState.Initial -> {
                    Text("Loading..")
                }
            }
        }
    }
}

@Composable
fun Success(successData: List<String>, drillDown: (String) -> Unit) {
    DogList(breeds = successData, drillDown)
}

@Composable
fun DogList(breeds: List<String>, onItemClick: (String) -> Unit) {
    LazyColumn {
        items(breeds) { breed ->
            DogRow(
                breed = breed,
                onClick = onItemClick,
            )
            Divider()
        }
    }
}

@Composable
fun DogRow(breed: String, onClick: (String) -> Unit) {
    Row(
        Modifier
            .clickable {
                onClick(breed)
            }
            .padding(10.dp)
    ) {
        Text(breed, Modifier.weight(1F))
    }
}

@Composable
fun DetailsScreen(
    onBack: () -> Unit = {},
    breedId: String = "",
    pictureViewModel: PictureViewModel = viewModel(),
) {
    val state by pictureViewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(pictureViewModel) {
        pictureViewModel.activate()
    }
    BackHandler {
        onBack()
    }
    Column {
        when (state) {
            is PictureViewState.Content -> {
                Text(text = "Showing details for breed ID -> $breedId")
            }
            PictureViewState.Loading -> {
                Text(text = "Loading")
            }
        }
    }
}