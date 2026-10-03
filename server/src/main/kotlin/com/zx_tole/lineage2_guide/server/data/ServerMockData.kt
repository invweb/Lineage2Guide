package com.zx_tole.lineage2_guide.server.data

import com.zx_tole.lineage2_guide.server.dto.*

object ServerMockData {

    // Current mock data (26 items total across all entities)
    // To use real data from lineage2-api repository:
    // 1. Download JSON files from https://github.com/cuteshaun/lineage2-api
    // 2. Compress with: gzip -9 filename.json
    // 3. Place .gz files in server/src/main/resources/
    // 4. Change 'useRealData' to true below
    
    private val useRealData = true
    
    val items by lazy { if (useRealData) RealDataLoader.loadItems() else mockItems }
    val quests by lazy { if (useRealData) RealDataLoader.loadQuests() else mockQuests }
    val skills by lazy { if (useRealData) RealDataLoader.loadSkills() else mockSkills }
    val classes by lazy { if (useRealData) RealDataLoader.loadClasses() else mockClasses }
    val npcs by lazy { if (useRealData) RealDataLoader.loadNpcs() else mockNpcs }
    
    // Mock data (small subset for fast loading)
    private val mockItems = listOf(
        ItemDto(1, "Adamantite Gaiters", "Warrior", 76, "Armor", "Legendary", "Olympiad Reward", "Olympiad Games", listOf(MapEntry("def", 120), MapEntry("maxHp", 200)), "Heavy armor made from adamantite.", null),
        ItemDto(2, "Scroll: Enchant Armor S", null, 40, "Scroll", "Rare", "Grand Boss", "Antharas, Valakas", listOf(MapEntry("enchantChance", 15)), "Increases enchant success rate.", null),
        ItemDto(3, "Raid Boss Health Potion", null, 10, "Consumable", "Common", "Merchant", null, listOf(MapEntry("hpRecovery", 500)), "Restores 500 HP.", null),
        ItemDto(4, "Draconic Leather Armor", "Mage", 80, "Armor", "Legendary", "Draconic Lord Boss", "Frintezza", listOf(MapEntry("def", 95), MapEntry("mp", 150)), "Light armor crafted from draconic scales.", null),
        ItemDto(5, "Soulshot: Grade A", null, 50, "Consumable", "Uncommon", "Merchant", null, listOf(MapEntry("attackBonus", 25)), "Increases physical attack power.", null),
        ItemDto(6, "Arcana Mace", "Mage", 75, "Weapon", "Epic", "God-Daoist", "Zaken", listOf(MapEntry("magicalAtk", 350), MapEntry("critical", 15)), "Powerful mace imbued with arcane energy.", null),
        ItemDto(7, "Adamantite Helmet", "Warrior", 76, "Armor", "Legendary", "Olympiad Reward", "Olympiad Games", listOf(MapEntry("def", 110), MapEntry("maxHp", 180)), "Heavy helmet made from adamantite.", null),
        ItemDto(8, "Blessed Scroll of Escape", null, 20, "Scroll", "Rare", "Quest Reward", "NPC: Village Master", listOf(MapEntry("escapeChance", 100)), "Guaranteed escape from any dungeon.", null)
    )
    
    private val mockQuests = listOf(
        QuestDto(1, "On Your Own Power", 101, 10, listOf(RewardDto("exp", 5000), RewardDto("sp", 2000)), "Prove your worth by defeating monsters.", "Main", emptyList(), "Complete"),
        QuestDto(2, "The Essence of War", 102, 30, listOf(RewardDto("item", 1), RewardDto("exp", 15000)), "Collect war essence from raid bosses.", "Side", listOf(1), "In Progress"),
        QuestDto(3, "Olympiad Preparation", 103, 60, listOf(RewardDto("olympiadTicket", 10)), "Complete trials to qualify for Olympiad.", "Main", listOf(2), "Not Started"),
        QuestDto(4, "Dragon's Breath", 104, 75, listOf(RewardDto("item", 2), RewardDto("exp", 50000)), "Investigate dragon activity.", "Raid", listOf(3), "Not Started")
    )
    
    private val mockSkills = listOf(
        SkillDto(1, "Power Strike", "Warrior", 1, "Physical", "A powerful strike.", 5, null, 10, "Melee"),
        SkillDto(2, "Magic Shield", "Mage", 10, "Defensive", "Creates a magical barrier.", 30, null, 50, "Self"),
        SkillDto(3, "Heal", "Cleric", 5, "Healing", "Restores health to an ally.", 10, null, 30, "300"),
        SkillDto(4, "Fireball", "Mage", 15, "Magical", "Hurls a ball of fire.", 8, null, 40, "400"),
        SkillDto(5, "Shield Bash", "Warrior", 20, "Crowd Control", "Stuns the target.", 20, null, 25, "Melee")
    )
    
    private val mockClasses = listOf(
        ClassDto(1, "Fighter", "All", listOf("Warrior", "Paladin", "Dark Avenger"), "Masters of melee combat.", null, emptyList()),
        ClassDto(2, "Mage", "All", listOf("Sorcerer", "Archmage", "Myrst"), "Powerful spellcasters.", null, emptyList()),
        ClassDto(3, "Cleric", "All", listOf("Oracle", "Saint"), "Healers and support specialists.", null, emptyList()),
        ClassDto(4, "Rogue", "All", listOf("Adventurer", "Treasure Hunter", "Blood Hunter"), "Stealthy fighters.", null, emptyList())
    )
    
    private val mockNpcs = listOf(
        NpcDto(101, "Village Master", "Quest Giver", "Every Village", "Offers beginner quests.", listOf(1), null),
        NpcDto(102, "Quartermaster", "Vendor", "Gludin Castle", "Sells rare items.", listOf(2), null),
        NpcDto(103, "Olympiad Administrator", "Quest Giver", "God's Valley Dungeon", "Manages Olympiad Games.", listOf(3), null),
        NpcDto(104, "Dragon Scholar", "Researcher", "Valley of Saints", "Studies dragon behavior.", listOf(4), null),
        NpcDto(105, "Antharas", "Raid Boss", "Orc's Lair", "Ancient dragon.", emptyList(), null)
    )
}
