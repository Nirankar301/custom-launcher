package com.nir.launcher.module

import com.nir.launcher.data.AppDrawerRepository
import com.nir.launcher.data.IAppDrawerRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dataModule = module {

    single<IAppDrawerRepository> { AppDrawerRepository(androidContext()) }
}