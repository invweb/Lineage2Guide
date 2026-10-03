package com.zx_tole.lineage2_guide.ui.navigation

sealed class Screen(val route: String) {
    object Items : Screen("items")
    object Quests : Screen("quests")
    object Skills : Screen("skills")
    object Classes : Screen("classes")
    object Npcs : Screen("npcs")
}
