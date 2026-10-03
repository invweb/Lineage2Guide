package com.zx_tole.lineage2_guide.server.data

import com.zx_tole.lineage2_guide.server.dto.*

private fun statsOf(vararg pairs: Pair<String, Int>) = pairs.map { MapEntry(it.first, it.second) }

object ServerMockData {

    val items = listOf(
        ItemDto(
            id = 1,
            name = "Adamantite Gaiters",
            classRestriction = "Warrior",
            level = 76,
            type = "Armor",
            rarity = "Legendary",
            location = "Olympiad Reward",
            dropInfo = "Olympiad Games",
            stats = statsOf("def" to 120, "maxHp" to 200),
            description = "Heavy armor made from adamantite. Provides exceptional protection.",
            iconUrl = null
        ),
        ItemDto(
            id = 2,
            name = "Scroll: Enchant Armor S",
            classRestriction = null,
            level = 40,
            type = "Scroll",
            rarity = "Rare",
            location = "Grand Boss",
            dropInfo = "Antharas, Valakas",
            stats = statsOf("enchantChance" to 15),
            description = "Increases enchant success rate for armor by 15%.",
            iconUrl = null
        ),
        ItemDto(
            id = 3,
            name = "Raid Boss Health Potion",
            classRestriction = null,
            level = 10,
            type = "Consumable",
            rarity = "Common",
            location = "Merchant",
            dropInfo = null,
            stats = statsOf("hpRecovery" to 500),
            description = "Restores 500 HP. Effective against raid bosses.",
            iconUrl = null
        ),
        ItemDto(
            id = 4,
            name = "Draconic Leather Armor",
            classRestriction = "Mage",
            level = 80,
            type = "Armor",
            rarity = "Legendary",
            location = "Draconic Lord Boss",
            dropInfo = "Frintezza",
            stats = statsOf("def" to 95, "mp" to 150, "mpRegen" to 5),
            description = "Light armor crafted from draconic scales.",
            iconUrl = null
        ),
        ItemDto(
            id = 5,
            name = "Soulshot: Grade A",
            classRestriction = null,
            level = 50,
            type = "Consumable",
            rarity = "Uncommon",
            location = "Merchant",
            dropInfo = null,
            stats = statsOf("attackBonus" to 25),
            description = "Increases physical attack power for 1 hour.",
            iconUrl = null
        ),
        ItemDto(
            id = 6,
            name = "Arcana Mace",
            classRestriction = "Mage",
            level = 75,
            type = "Weapon",
            rarity = "Epic",
            location = "God-Daoist",
            dropInfo = "Zaken",
            stats = statsOf("magicalAtk" to 350, "critical" to 15),
            description = "A powerful mace imbued with arcane energy.",
            iconUrl = null
        ),
        ItemDto(
            id = 7,
            name = "Adamantite Helmet",
            classRestriction = "Warrior",
            level = 76,
            type = "Armor",
            rarity = "Legendary",
            location = "Olympiad Reward",
            dropInfo = "Olympiad Games",
            stats = statsOf("def" to 110, "maxHp" to 180),
            description = "Heavy helmet made from adamantite.",
            iconUrl = null
        ),
        ItemDto(
            id = 8,
            name = "Blessed Scroll of Escape",
            classRestriction = null,
            level = 20,
            type = "Scroll",
            rarity = "Rare",
            location = "Quest Reward",
            dropInfo = "NPC: Village Master",
            stats = statsOf("escapeChance" to 100),
            description = "Guaranteed escape from any dungeon.",
            iconUrl = null
        )
    )

    val quests = listOf(
        QuestDto(
            id = 1,
            name = "On Your Own Power",
            npcId = 101,
            startLevel = 10,
            reward = listOf(RewardDto("exp", 5000), RewardDto("sp", 2000)),
            description = "Prove your worth by defeating monsters in the starting area.",
            type = "Main",
            prerequisites = emptyList(),
            progressStatus = "Complete"
        ),
        QuestDto(
            id = 2,
            name = "The Essence of War",
            npcId = 102,
            startLevel = 30,
            reward = listOf(RewardDto("item", 1), RewardDto("exp", 15000)),
            description = "Collect war essence from raid bosses and deliver it to the quartermaster.",
            type = "Side",
            prerequisites = listOf(1),
            progressStatus = "In Progress"
        ),
        QuestDto(
            id = 3,
            name = "Olympiad Preparation",
            npcId = 103,
            startLevel = 60,
            reward = listOf(RewardDto("olympiadTicket", 10)),
            description = "Complete trials to qualify for the Olympiad Games.",
            type = "Main",
            prerequisites = listOf(2),
            progressStatus = "Not Started"
        ),
        QuestDto(
            id = 4,
            name = "Dragon's Breath",
            npcId = 104,
            startLevel = 75,
            reward = listOf(RewardDto("item", 2), RewardDto("exp", 50000)),
            description = "Investigate dragon activity in the northern mountains.",
            type = "Raid",
            prerequisites = listOf(3),
            progressStatus = "Not Started"
        )
    )

    val skills = listOf(
        SkillDto(
            id = 1,
            name = "Power Strike",
            classRestriction = "Warrior",
            level = 1,
            type = "Physical",
            description = "A powerful strike that deals increased damage.",
            cooldown = 5,
            iconUrl = null,
            manaCost = 10,
            range = "Melee"
        ),
        SkillDto(
            id = 2,
            name = "Magic Shield",
            classRestriction = "Mage",
            level = 10,
            type = "Defensive",
            description = "Creates a magical barrier that absorbs damage.",
            cooldown = 30,
            iconUrl = null,
            manaCost = 50,
            range = "Self"
        ),
        SkillDto(
            id = 3,
            name = "Heal",
            classRestriction = "Cleric",
            level = 5,
            type = "Healing",
            description = "Restores health to an ally.",
            cooldown = 10,
            iconUrl = null,
            manaCost = 30,
            range = "300"
        ),
        SkillDto(
            id = 4,
            name = "Fireball",
            classRestriction = "Mage",
            level = 15,
            type = "Magical",
            description = "Hurls a ball of fire at enemies.",
            cooldown = 8,
            iconUrl = null,
            manaCost = 40,
            range = "400"
        ),
        SkillDto(
            id = 5,
            name = "Shield Bash",
            classRestriction = "Warrior",
            level = 20,
            type = "Crowd Control",
            description = "Stuns the target with a shield strike.",
            cooldown = 20,
            iconUrl = null,
            manaCost = 25,
            range = "Melee"
        )
    )

    val classes = listOf(
        ClassDto(
            id = 1,
            name = "Fighter",
            race = "All",
            subClasses = listOf("Warrior", "Paladin", "Dark Avenger"),
            description = "Masters of melee combat with high physical damage.",
            iconUrl = null,
            baseStats = statsOf("str" to 15, "dex" to 10, "con" to 15)
        ),
        ClassDto(
            id = 2,
            name = "Mage",
            race = "All",
            subClasses = listOf("Sorcerer", "Archmage", "Myrst"),
            description = "Powerful spellcasters with devastating magical abilities.",
            iconUrl = null,
            baseStats = statsOf("int" to 18, "wis" to 15, "men" to 10)
        ),
        ClassDto(
            id = 3,
            name = "Cleric",
            race = "All",
            subClasses = listOf("Oracle", "Saint"),
            description = "Healers and support specialists.",
            iconUrl = null,
            baseStats = statsOf("int" to 12, "wis" to 18, "con" to 10)
        ),
        ClassDto(
            id = 4,
            name = "Rogue",
            race = "All",
            subClasses = listOf("Adventurer", "Treasure Hunter", "Blood Hunter"),
            description = "Stealthy fighters specializing in critical hits and evasion.",
            iconUrl = null,
            baseStats = statsOf("dex" to 16, "str" to 8, "con" to 12)
        )
    )

    val npcs = listOf(
        NpcDto(
            id = 101,
            name = "Village Master",
            type = "Quest Giver",
            location = "Every Village",
            description = "Offers beginner quests and guides new players.",
            relatedQuestIds = listOf(1),
            iconUrl = null
        ),
        NpcDto(
            id = 102,
            name = "Quartermaster",
            type = "Vendor",
            location = "Gludin Castle",
            description = "Sells rare items and quest materials.",
            relatedQuestIds = listOf(2),
            iconUrl = null
        ),
        NpcDto(
            id = 103,
            name = "Olympiad Administrator",
            type = "Quest Giver",
            location = "God's Valley Dungeon",
            description = "Manages Olympiad Games registration.",
            relatedQuestIds = listOf(3),
            iconUrl = null
        ),
        NpcDto(
            id = 104,
            name = "Dragon Scholar",
            type = "Researcher",
            location = "Valley of Saints",
            description = "Studies dragon behavior and provides raid information.",
            relatedQuestIds = listOf(4),
            iconUrl = null
        ),
        NpcDto(
            id = 105,
            name = "Antharas",
            type = "Raid Boss",
            location = "Orc's Lair",
            description = "Ancient dragon that appears every 4 days.",
            relatedQuestIds = emptyList(),
            iconUrl = null
        )
    )
}
