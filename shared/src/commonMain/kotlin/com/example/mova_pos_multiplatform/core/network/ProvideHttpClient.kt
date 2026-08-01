package com.example.mova_pos_multiplatform.core.network

import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.URLProtocol
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

fun provideHttpClient(mockEngine: MockEngine): HttpClient {
    return HttpClient(engine = mockEngine) {
        install(plugin = ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                prettyPrint = true
            })
        }
        install(plugin = DefaultRequest) {
            url {
                protocol = URLProtocol.HTTPS
                host = "api.example"
            }
        }
    }.apply {
        addErrorInterceptor()
    }
}
