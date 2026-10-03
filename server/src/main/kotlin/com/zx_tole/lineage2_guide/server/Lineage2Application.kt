package com.zx_tole.lineage2_guide.server

import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun main() {
    val port = System.getenv("PORT")?.toIntOrNull() ?: 8080
    
    embeddedServer(Netty, port = port) {
        module()
    }.start(wait = true)
}
