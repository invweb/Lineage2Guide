package com.zx_tole.lineage2_guide.data.local

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class Lineage2Database {
    // Stub - SQLDelight code generation requires plugin which is incompatible with AGP 9.0 KMP
    // The .sq files in sqldelight/ will be used once the plugin issue is resolved
    fun <T> createFlow(data: List<T>): Flow<List<T>> = flow { emit(data) }
}
