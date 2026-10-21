package com.zx_tole.lineage2_guide.common

import platform.Foundation.NSLog

actual object Logger {
    actual fun d(message: String) {
        NSLog("DEBUG: $message")
    }
    
    actual fun e(message: String, throwable: Throwable?) {
        if (throwable != null) {
            NSLog("ERROR: $message - ${throwable.stackTraceToString()}")
        } else {
            NSLog("ERROR: $message")
        }
    }
    
    actual fun i(message: String) {
        NSLog("INFO: $message")
    }
    
    actual fun w(message: String) {
        NSLog("WARN: $message")
    }
}
