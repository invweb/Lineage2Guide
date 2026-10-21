package com.zx_tole.lineage2_guide.ui.navigation

sealed class Screen(val route: String) {
    object Items : Screen("items")
    object Quests : Screen("quests")
    object Skills : Screen("skills")
    object Classes : Screen("classes")
    object Npcs : Screen("npcs")
    
    companion object {
        const val ITEM_DETAIL = "item_detail/{itemId}"
        const val QUEST_DETAIL = "quest_detail/{questId}"
        const val SKILL_DETAIL = "skill_detail/{skillId}"
        const val CLASS_DETAIL = "class_detail/{classId}"
        const val NPC_DETAIL = "npc_detail/{npcId}"
    }
}
