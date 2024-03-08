package com.r42914lg.vmnav.nav

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.r42914lg.vmnav.routes.DetailsPageRoute
import com.r42914lg.vmnav.routes.ListPageRoute

@Composable
fun NavigationComponent(navHostController: NavHostController) {
    NavHost(
        navController = navHostController,
        startDestination = ListPageRoute.getRoute(),
    ) {
        ListPageRoute.composable(this, navHostController)
        DetailsPageRoute.composable(this, navHostController)
    }
}