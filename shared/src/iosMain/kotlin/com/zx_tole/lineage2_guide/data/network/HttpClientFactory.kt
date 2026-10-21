package com.zx_tole.lineage2_guide.data.network

import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json

actual fun createHttpClient(baseUrl: String): HttpClient {
    return HttpClient {
        install(ContentNegotiation) {
            json()
        }
    }
}
