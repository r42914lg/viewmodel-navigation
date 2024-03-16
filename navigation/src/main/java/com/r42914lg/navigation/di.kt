package com.r42914lg.navigation

import org.koin.dsl.module

val navModule = module {
    single<RouteNavigator> { MyRouteNavigator() }
}