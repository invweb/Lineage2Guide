package com.zx_tole.lineage2_guide.common

expect object Logger {
    fun d(message: String)
    fun e(message: String, throwable: Throwable? = null)
    fun i(message: String)
    fun w(message: String)
}
