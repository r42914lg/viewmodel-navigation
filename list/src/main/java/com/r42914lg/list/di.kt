package com.r42914lg.list

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val listModule = module {
    viewModel { BreedViewModel(get()) }
}