package com.r42914lg.list

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.items
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun MainScreen(
    viewModel: BreedViewModel = viewModel(),
) {
    val dogsState by viewModel.breedState.collectAsStateWithLifecycle()

    LaunchedEffect(viewModel) {
        viewModel.activate()
    }
    BackHandler {
        viewModel.onBack()
    }
    MainScreenContent(
        dogsState = dogsState,
        onBreedSelected = { breed -> viewModel.onToDetailScreen(breed = breed) }
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
