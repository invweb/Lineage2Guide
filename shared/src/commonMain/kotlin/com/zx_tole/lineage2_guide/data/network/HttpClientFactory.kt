package com.zx_tole.lineage2_guide.data.network

import io.ktor.client.HttpClient

expect fun createHttpClient(baseUrl: String): HttpClient
