package com.rony.tondo_smart.data.di

import com.rony.tondo_smart.data.RemoteSchemaDataSource
import com.rony.tondo_smart.domain.SchemaRepository
import com.rony.tondo_smart.ui.MainViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.plus
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {

    single<CoroutineDispatcher> {
        Dispatchers.Default
    }

    single<SchemaRepository> {
        RemoteSchemaDataSource(get())
    }


    viewModelOf(::MainViewModel)
}