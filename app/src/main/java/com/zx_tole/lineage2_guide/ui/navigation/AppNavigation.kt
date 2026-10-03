package com.zx_tole.lineage2_guide.ui.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.koin.androidx.compose.koinViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.zx_tole.lineage2_guide.ui.classes.ClassesScreen
import com.zx_tole.lineage2_guide.ui.common.BottomNavigation
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
    Column(modifier = modifier.fillMaxSize()) {
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
