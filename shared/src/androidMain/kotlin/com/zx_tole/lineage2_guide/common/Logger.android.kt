package com.zx_tole.lineage2_guide.common

import timber.log.Timber

actual object Logger {
    actual fun d(message: String) {
        Timber.d(message)
    }
    
    actual fun e(message: String, throwable: Throwable?) {
        if (throwable != null) {
            Timber.e(throwable, message)
        } else {
            Timber.e(message)
        }
    }
    
    actual fun i(message: String) {
        Timber.i(message)
    }
    
    actual fun w(message: String) {
        Timber.w(message)
    }
}
