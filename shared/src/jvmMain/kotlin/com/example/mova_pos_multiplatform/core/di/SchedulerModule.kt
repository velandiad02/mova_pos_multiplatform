package com.example.mova_pos_multiplatform.core.di

import com.example.mova_pos_multiplatform.feature.transactions.data.DesktopNetworkObserver
import com.example.mova_pos_multiplatform.feature.transactions.data.TransactionsSyncSchedulerImpl
import com.example.mova_pos_multiplatform.feature.transactions.domain.boundary.TransactionsSyncScheduler
import com.example.mova_pos_multiplatform.feature.transactions.domain.scheduler.TransactionsSyncSchedulerLauncher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import org.koin.core.module.dsl.singleOf
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module
import kotlin.coroutines.CoroutineContext

val schedulerModule = module {

    single {
        CoroutineScope(
            context = get<CoroutineContext>(named(DispatchersNamed.IO)) + SupervisorJob(),
        )
    }

    singleOf(constructor = ::TransactionsSyncSchedulerImpl) bind TransactionsSyncScheduler::class
    singleOf(constructor = ::DesktopNetworkObserver)

    single { TransactionsSyncSchedulerLauncher(transactionsSyncScheduler = get()) }
}