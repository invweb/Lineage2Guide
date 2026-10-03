package com.zx_tole.lineage2_guide

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.zx_tole.lineage2_guide.ui.navigation.AppNavigation
import com.zx_tole.lineage2_guide.ui.theme.Lineage2Theme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var isDarkTheme by mutableStateOf(true)
            Lineage2Theme(darkTheme = isDarkTheme) {
                AppNavigation()
            }
        }
    }
}
