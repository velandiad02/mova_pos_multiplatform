package com.example.mova_pos_multiplatform.feature.transactions.data

import com.example.mova_pos_multiplatform.core.common.error.AppException
import com.example.mova_pos_multiplatform.core.common.error.ErrorStatus
import com.example.mova_pos_multiplatform.feature.transactions.domain.boundary.TransactionsSyncScheduler
import com.example.mova_pos_multiplatform.feature.transactions.domain.use_case.SyncTransactionsUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.util.logging.Logger

class TransactionsSyncSchedulerImpl(
    private val syncTransactionsUseCase: SyncTransactionsUseCase,
    private val networkObserver: DesktopNetworkObserver,
    private val scope: CoroutineScope,
) : TransactionsSyncScheduler {

    private val logger = Logger.getLogger("SyncScheduler")
    private val syncChannel = Channel<String>(capacity = Channel.RENDEZVOUS)

    init {
        scope.launch {
            for (terminalId in syncChannel) {
                logger.info("Signals received from channel. Starting processWithRetries().")
                processWithRetries(terminalId = terminalId)
            }
        }
    }

    override fun scheduleSync(terminalId: String) {
        logger.info("scheduleSync() called. Sending signal to channel.")
        val result = syncChannel.trySend(element = terminalId)

        if (result.isFailure) {
            logger.warning("The canal is already full.")
        }
    }

    private suspend fun processWithRetries(terminalId: String) {
        var attempt = 1
        val maxAttempts = 3

        logger.info("Starting sync routine (Max attempts: $maxAttempts).")

        while (attempt <= maxAttempts) {

            if (!networkObserver.isConnected()) {
                logger.warning("[Attempt $attempt/$maxAttempts] No network connection. Suspending until network is back...")

                networkObserver.observeConnectivity().first { isConnected -> isConnected }

                logger.info("Network restored! Resuming sync process...")
            }

            val mustSaveErrors = (attempt == maxAttempts)

            logger.info("[Attempt $attempt/$maxAttempts] Network connected. Executing syncUseCase (mustSaveErrors=$mustSaveErrors)...")

            val result = syncTransactionsUseCase(
                terminalId = terminalId,
                mustSaveErrors = mustSaveErrors,
            )

            if (result.isSuccess) {
                logger.info("[Attempt $attempt/$maxAttempts] Transactions synced successfully!")
                break
            }

            val exception: AppException? = result.exceptionOrNull() as? AppException

            logger.warning("[Attempt $attempt/$maxAttempts] Sync failed. Cause: ${exception?.message ?: "Unknown error"}")

            if (exception?.status == ErrorStatus.RETRYABLE) {
                logger.info("Retryable error detected (${exception.status}).")
                attempt++

                if (attempt < maxAttempts) {
                    logger.info("Waiting 10s before retry #${attempt + 1}...")
                    delay(timeMillis = 10_000L)
                }
            } else {
                logger.severe("Non-retryable error encountered (Status: ${exception?.status}). Aborting further attempts.")
                break
            }
        }

        logger.info("Sync routine completed.")
    }
}