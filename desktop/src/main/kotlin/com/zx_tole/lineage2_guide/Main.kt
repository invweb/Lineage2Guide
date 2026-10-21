package com.zx_tole.lineage2_guide

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPlacement
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.application.ApplicationScope
import androidx.compose.ui.application.LocalApplication
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.component.KoinComponent
import org.koin.dsl.module
import kotlinx.coroutines.swing.SwingDispatcher

fun main() {
    // Initialize Koin
    val koin = startKoin {
        modules(
            module {
                // Desktop-specific dependencies if needed
            }
        )
    }
    
    try {
        Window(
            title = "Lineage2Guide",
            size = androidx.compose.ui.geometry.Size(1200f, 800f),
            placement = WindowPlacement.Maximized,
            positionOnFirstShow = WindowPosition.Aligned(Alignment.Center)
        ) {
            val appState = rememberAppState()
            
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    if (appState.isLoading) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    } else {
                        MainScreen(
                            navController = appState.navController,
                            onNavigate = appState::navigate
                        )
                    }
                }
            }
        }
    } finally {
        stopKoin()
    }
}

@Composable
private fun rememberAppState(): AppState {
    return remember { AppState() }
}

class AppState : KoinComponent {
    var navController by mutableStateOf(NavController())
        private set
    
    var isLoading by mutableStateOf(false)
        private set
    
    fun navigate(route: String) {
        navController.navigate(route)
    }
}

class NavController {
    var currentRoute by mutableStateOf("home")
        private set
    
    fun navigate(route: String) {
        currentRoute = route
    }
}

@Composable
private fun MainScreen(
    navController: NavController,
    onNavigate: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Lineage2Guide Desktop",
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
        ) {
            Text(
                text = "Kotlin Multiplatform - Desktop",
                modifier = Modifier.padding(16.dp)
            )
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Text(
            text = "This is a placeholder for the Desktop UI. The full UI will be implemented using Compose Multiplatform.",
            style = MaterialTheme.typography.bodyLarge
        )
    }
}
