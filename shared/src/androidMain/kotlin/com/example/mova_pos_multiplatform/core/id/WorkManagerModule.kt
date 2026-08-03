package com.example.mova_pos_multiplatform.core.id

import com.example.mova_pos_multiplatform.feature.transactions.data.workManager.TransactionsSyncWorkManagerImpl
import com.example.mova_pos_multiplatform.feature.transactions.data.workManager.TransactionsSyncWorker
import com.example.mova_pos_multiplatform.feature.transactions.domain.boundary.TransactionsSyncWorkManager
import com.example.mova_pos_multiplatform.feature.transactions.domain.scheduler.TransactionsSyncSchedulerLauncher
import org.koin.androidx.workmanager.dsl.workerOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val workManagerModule = module {

    workerOf(constructor = ::TransactionsSyncWorker)

    singleOf(constructor = ::TransactionsSyncWorkManagerImpl) bind TransactionsSyncWorkManager::class

    singleOf(constructor = ::TransactionsSyncSchedulerLauncher)
}