package com.example.mova_pos_multiplatform.core.di

import kotlinx.coroutines.Dispatchers
import org.koin.core.qualifier.named
import org.koin.dsl.module
import kotlin.coroutines.CoroutineContext

object DispatchersNamed {
    const val IO: String = "DispatcherIO"
}

val dispatcherModule = module {

    single<CoroutineContext>(qualifier = named(DispatchersNamed.IO)) { Dispatchers.IO }
}