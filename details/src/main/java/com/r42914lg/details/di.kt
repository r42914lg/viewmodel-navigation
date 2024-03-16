package com.r42914lg.details

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val detailsModule = module {
    viewModel { params ->
        PictureViewModel(
            params[0],
            params[1],
            get()
        )
    }
}