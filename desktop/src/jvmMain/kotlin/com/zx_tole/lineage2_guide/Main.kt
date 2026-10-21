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
import androidx.compose.ui.window.application
import org.koin.core.context.startKoin
import org.koin.dsl.module

fun main() {
    // Initialize Koin
    startKoin {
        modules(
            module {
                // Desktop-specific dependencies if needed
            }
        )
    }
    
    application {
        Window(
            title = "Lineage2Guide",
            onCloseRequest = { exitApplication() }
        ) {
            DesktopApp()
        }
    }
}

@Composable
fun DesktopApp() {
    MaterialTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Lineage2Guide Desktop",
                    style = MaterialTheme.typography.headlineLarge
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                Card(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Kotlin Multiplatform - Desktop",
                        modifier = Modifier.padding(16.dp)
                    )
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                
                Text(
                    text = "Compose Multiplatform Desktop UI",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}
