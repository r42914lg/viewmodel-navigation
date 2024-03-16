package com.r42914lg.list

import androidx.compose.runtime.Composable
import org.koin.androidx.compose.koinViewModel

object ListPageRoute : com.r42914lg.navigation.NavRoute<com.r42914lg.list.BreedViewModel>() {
    override val route = "screen_list"

    override fun getRoute(vararg params: Any) = route

    @Composable
    override fun viewModel(): BreedViewModel = koinViewModel()
    @Composable
    override fun Content(viewModel: BreedViewModel) = MainScreen(viewModel)
}
