package com.zx_tole.lineage2_guide

import android.app.Application
import android.content.Context
import com.zx_tole.lineage2_guide.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import timber.log.Timber

class Lineage2App : Application() {

    companion object {
        @Volatile
        private var INSTANCE: Lineage2App? = null

        fun getInstance(): Lineage2App? = INSTANCE

        fun Context.app(): Lineage2App = applicationContext as Lineage2App
    }

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        val useMockServer = BuildConfig.DEBUG
        val baseUrl = if (useMockServer) {
            "http://10.0.2.2:8080/" // Android emulator -> localhost
        } else {
            BuildConfig.API_BASE_URL
        }

        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@Lineage2App)
            modules(appModule(baseUrl))
        }
    }
}
