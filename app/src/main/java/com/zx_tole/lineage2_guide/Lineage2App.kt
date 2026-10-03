package com.zx_tole.lineage2_guide

import android.app.Application
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import com.zx_tole.lineage2_guide.di.appModule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
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
            "http://192.168.0.8:8080/api/v1/" // Local server IP (use 10.0.2.2 for emulator)
        } else {
            BuildConfig.API_BASE_URL
        }

        Log.d("Lineage2App", "Starting Koin with baseUrl: $baseUrl")

        stopKoin()
        
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@Lineage2App)
            modules(appModule(baseUrl))
        }

        // Load initial data from server
        loadInitialData()
    }
    
    private fun loadInitialData() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val koin = org.koin.core.context.GlobalContext.get()
                val itemsRepo = koin.get<com.zx_tole.lineage2_guide.domain.repository.ItemsRepository>()
                
                itemsRepo.refreshItems()
                Log.d("Lineage2App", "Items refresh completed")
                
                Handler(Looper.getMainLooper()).post {
                    Log.d("Lineage2App", "Initial data loading completed")
                }
            } catch (e: Exception) {
                Log.e("Lineage2App", "Failed to load initial data", e)
            }
        }
    }
}
