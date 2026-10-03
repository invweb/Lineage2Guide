package com.zx_tole.lineage2_guide.ui.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import org.koin.androidx.compose.koinViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.zx_tole.lineage2_guide.ui.classes.ClassesScreen
import com.zx_tole.lineage2_guide.ui.common.BottomNavigation
import com.zx_tole.lineage2_guide.ui.common.ConnectivityChecker
import com.zx_tole.lineage2_guide.ui.common.OfflineBanner
import com.zx_tole.lineage2_guide.ui.items.ItemsScreen
import com.zx_tole.lineage2_guide.ui.items.ItemsViewModel
import com.zx_tole.lineage2_guide.ui.npcs.NpcsScreen
import com.zx_tole.lineage2_guide.ui.quests.QuestsScreen
import com.zx_tole.lineage2_guide.ui.skills.SkillsScreen

@Composable
fun AppNavigation(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    val context = LocalContext.current
    val isConnected = ConnectivityChecker.isConnected.collectAsState()
    
    // Initialize connectivity checker
    LaunchedEffect(Unit) {
        ConnectivityChecker.initialize(context)
    }
    
    Column(modifier = modifier.fillMaxSize()) {
        OfflineBanner(modifier = Modifier.padding(bottom = 4.dp))
        
        NavHost(
            navController = navController,
            startDestination = Screen.Items.route
        ) {
            composable(Screen.Items.route) {
                val viewModel: ItemsViewModel = koinViewModel()
                ItemsScreen(
                    viewModel = viewModel,
                    onNavigateToDetail = { /* TODO: navigate to item detail */ },
                    onNavigateToClassDetail = { /* TODO: navigate to class detail */ }
                )
            }
            composable(Screen.Quests.route) { QuestsScreen() }
            composable(Screen.Skills.route) { SkillsScreen() }
            composable(Screen.Classes.route) { ClassesScreen() }
            composable(Screen.Npcs.route) { NpcsScreen() }
        }

        BottomNavigation(navController = navController)
    }
}
