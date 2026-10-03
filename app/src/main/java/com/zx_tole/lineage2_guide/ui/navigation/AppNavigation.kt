package com.zx_tole.lineage2_guide.ui.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.zx_tole.lineage2_guide.ui.classes.ClassesScreen
import com.zx_tole.lineage2_guide.ui.classes.ClassDetailScreen
import com.zx_tole.lineage2_guide.ui.common.BottomNavigation
import com.zx_tole.lineage2_guide.ui.common.ConnectivityChecker
import com.zx_tole.lineage2_guide.ui.common.OfflineBanner
import com.zx_tole.lineage2_guide.ui.items.ItemDetailScreen
import com.zx_tole.lineage2_guide.ui.items.ItemsScreen
import com.zx_tole.lineage2_guide.ui.items.ItemsViewModel
import com.zx_tole.lineage2_guide.ui.npcs.NpcsScreen
import com.zx_tole.lineage2_guide.ui.npcs.NpcDetailScreen
import com.zx_tole.lineage2_guide.ui.quests.QuestsScreen
import com.zx_tole.lineage2_guide.ui.quests.QuestDetailScreen
import com.zx_tole.lineage2_guide.ui.skills.SkillsScreen
import com.zx_tole.lineage2_guide.ui.skills.SkillDetailScreen
import org.koin.androidx.compose.koinViewModel

@Composable
fun AppNavigation(
    modifier: androidx.compose.ui.Modifier = androidx.compose.ui.Modifier,
    navController: NavHostController = rememberNavController()
) {
    val context = LocalContext.current
    val isConnected = ConnectivityChecker.isConnected.collectAsState()
    
    // Initialize connectivity checker
    LaunchedEffect(Unit) {
        ConnectivityChecker.initialize(context)
    }
    
    Scaffold(
        modifier = modifier,
        bottomBar = {
            BottomNavigation(navController = navController)
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            OfflineBanner()
            
            NavHost(
                navController = navController,
                startDestination = Screen.Items.route
            ) {
                composable(Screen.Items.route) {
                    val viewModel: ItemsViewModel = koinViewModel()
                    ItemsScreen(
                        viewModel = viewModel,
                        onNavigateToDetail = { itemId ->
                            navController.navigate(Screen.ITEM_DETAIL.replace("{itemId}", itemId.toString()))
                        },
                        onNavigateToClassDetail = { /* TODO: navigate to class detail */ }
                    )
                }
                composable(Screen.Quests.route) {
                    QuestsScreen(
                        onNavigateToDetail = { questId ->
                            navController.navigate(Screen.QUEST_DETAIL.replace("{questId}", questId.toString()))
                        }
                    )
                }
                composable(Screen.Skills.route) {
                    SkillsScreen(
                        onNavigateToDetail = { skillId ->
                            navController.navigate(Screen.SKILL_DETAIL.replace("{skillId}", skillId.toString()))
                        }
                    )
                }
                composable(Screen.Classes.route) {
                    ClassesScreen(
                        onNavigateToDetail = { classId ->
                            navController.navigate(Screen.CLASS_DETAIL.replace("{classId}", classId.toString()))
                        }
                    )
                }
                composable(Screen.Npcs.route) {
                    NpcsScreen(
                        onNavigateToDetail = { npcId ->
                            navController.navigate(Screen.NPC_DETAIL.replace("{npcId}", npcId.toString()))
                        }
                    )
                }
                composable(Screen.ITEM_DETAIL) { backStackEntry ->
                    val itemId = backStackEntry.arguments?.getString("itemId")?.toLongOrNull() ?: return@composable
                    ItemDetailScreen(
                        itemId = itemId,
                        onNavigateBack = { navController.popBackStack() }
                    )
                }
                composable(Screen.QUEST_DETAIL) { backStackEntry ->
                    val questId = backStackEntry.arguments?.getString("questId")?.toLongOrNull() ?: return@composable
                    QuestDetailScreen(
                        questId = questId,
                        onNavigateBack = { navController.popBackStack() }
                    )
                }
                composable(Screen.SKILL_DETAIL) { backStackEntry ->
                    val skillId = backStackEntry.arguments?.getString("skillId")?.toLongOrNull() ?: return@composable
                    SkillDetailScreen(
                        skillId = skillId,
                        onNavigateBack = { navController.popBackStack() }
                    )
                }
                composable(Screen.CLASS_DETAIL) { backStackEntry ->
                    val classId = backStackEntry.arguments?.getString("classId")?.toLongOrNull() ?: return@composable
                    ClassDetailScreen(
                        classId = classId,
                        onNavigateBack = { navController.popBackStack() }
                    )
                }
                composable(Screen.NPC_DETAIL) { backStackEntry ->
                    val npcId = backStackEntry.arguments?.getString("npcId")?.toLongOrNull() ?: return@composable
                    NpcDetailScreen(
                        npcId = npcId,
                        onNavigateBack = { navController.popBackStack() }
                    )
                }
            }
        }
    }
}
