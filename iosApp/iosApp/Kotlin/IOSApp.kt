package com.zx_tole.lineage2_guide.ios

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.collectAsState
import com.zx_tole.lineage2_guide.ui.items.ItemsScreen
import com.zx_tole.lineage2_guide.ui.theme.Lineage2Theme
import org.koin.core.component.KoinComponent
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module

@Composable
fun IOSApp() {
    Lineage2Theme {
        ItemsScreen(
            onNavigateToDetail = { itemId ->
                // Navigate to detail
            }
        )
    }
}

class IOSAppKoinInitializer : KoinComponent {
    fun initialize() {
        startKoin {
            modules(
                module {
                    // iOS-specific dependencies
                }
            )
        }
    }
}
