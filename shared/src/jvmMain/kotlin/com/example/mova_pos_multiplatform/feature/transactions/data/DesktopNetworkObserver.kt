package com.example.mova_pos_multiplatform.feature.transactions.data

import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.isActive
import java.net.InetSocketAddress
import java.net.Socket

class DesktopNetworkObserver {

    fun isConnected(): Boolean {
        return try {
            val socket = Socket()

            socket.connect(
                InetSocketAddress("8.8.8.8", 53),
                1500,
            )

            socket.close()

            true
        } catch (_: Exception) {
            false
        }
    }

    fun observeConnectivity(checkIntervalMs: Long = 5_000L): Flow<Boolean> = flow {
        while (currentCoroutineContext().isActive) {
            emit(value = isConnected())
            delay(timeMillis = checkIntervalMs)
        }
    }.distinctUntilChanged()

}