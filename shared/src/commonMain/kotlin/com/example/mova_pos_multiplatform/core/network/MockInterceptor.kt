package com.example.mova_pos_multiplatform.core.network

import com.example.mova_pos_multiplatform.feature.commerce_terminal.data.remote.dto.CommerceResponseDto
import com.example.mova_pos_multiplatform.feature.commerce_terminal.data.remote.dto.TerminalResponseDto
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.engine.mock.respondError
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import kotlinx.coroutines.delay
import kotlinx.serialization.json.Json

val commerces = mutableListOf(
    CommerceResponseDto(id = "c-001", name = "Cafetería La 85", nit = "900123456-1"),
    CommerceResponseDto(id = "c-002", name = "Ferretería El Tornillo S.A.S.", nit = "830987654-3"),
    CommerceResponseDto(id = "c-003", name = "Droguería VitalPlus", nit = "901456789-8"),
    CommerceResponseDto(id = "c-004", name = "Minimercado El Ahorro", nit = "800555444-2")
)

val terminals = mutableListOf(
    TerminalResponseDto(id = "t-101", commerceId = "c-001", name = "POS-CAJA-01"),
    TerminalResponseDto(id = "t-102", commerceId = "c-001", name = "POS-BARRA-02"),

    TerminalResponseDto(id = "t-201", commerceId = "c-002", name = "POS-MOSTRADOR-A"),
    TerminalResponseDto(id = "t-202", commerceId = "c-002", name = "POS-CAJA-RAPIDA"),

    TerminalResponseDto(id = "t-301", commerceId = "c-003", name = "POS-FARMA-01"),

    TerminalResponseDto(id = "t-401", commerceId = "c-004", name = "POS-PRINCIPAL"),
    TerminalResponseDto(id = "t-402", commerceId = "c-004", name = "POS-SECUNDARIA"),
    TerminalResponseDto(id = "t-403", commerceId = "c-004", name = "POS-AUTOSERVICIO")
)

fun getMockEngine(): MockEngine = MockEngine { request ->
    delay(timeMillis = 3000)

    val responseHeaders = headersOf(name = HttpHeaders.ContentType, value = "application/json")
    val path = request.url.encodedPath

    when {
        path == "/commerces" -> {
            respond(
                content = Json.encodeToString(value = commerces),
                status = HttpStatusCode.OK,
                headers = responseHeaders,
            )
        }
        path.startsWith(prefix = "/terminals/") -> {
            val commerceId = path.removePrefix("/terminals/")
            val filteredTerminals = terminals.filter { it.commerceId == commerceId }

            respond(
                content = Json.encodeToString(value = filteredTerminals),
                status = HttpStatusCode.OK,
                headers = responseHeaders,
            )
        }
        else -> respondError(status = HttpStatusCode.NotFound)
    }
}