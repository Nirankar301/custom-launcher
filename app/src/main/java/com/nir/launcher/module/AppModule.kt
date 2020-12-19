package com.nir.launcher.module

import com.nir.launcher.viewmodel.AppDrawerViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    viewModel {
        AppDrawerViewModel(get())
    }
}