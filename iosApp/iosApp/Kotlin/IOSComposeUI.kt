package com.zx_tole.lineage2_guide.ios

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.viewinterop.AndroidView
import com.zx_tole.lineage2_guide.di.initKoin
import com.zx_tole.lineage2_guide.ui.navigation.AppNavigation
import com.zx_tole.lineage2_guide.ui.theme.Lineage2Theme
import org.koin.core.component.KoinComponent
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module

// iOS-specific Koin initialization
class IOSAppKoinInitializer : KoinComponent {
    fun initialize() {
        // Initialize Koin with shared modules
        startKoin {
            // iOS-specific modules can be added here
            modules(
                module {
                    // iOS-specific dependencies
                }
            )
        }
    }
}

// Compose UI for iOS
@Composable
fun IOSComposeUI() {
    Lineage2Theme {
        AppNavigation()
    }
}
