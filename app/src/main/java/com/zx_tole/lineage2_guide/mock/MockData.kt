package com.zx_tole.lineage2_guide.mock

import com.zx_tole.lineage2_guide.data.network.dto.*

object MockData {

    val items = listOf(
        ItemDto(
            id = 1,
            name = "Draconic Sword",
            classRestriction = "Warlord",
            level = 80,
            type = "WEAPON",
            rarity = "LEGENDARY",
            location = "Antharas Drop",
            dropInfo = "Dropped by Antharas, 15% chance",
            stats = mapOf("atk" to 350, "critRate" to 15, "speed" to 120),
            description = "A legendary blade forged from dragon scales. Deals massive damage to undead creatures.",
            iconUrl = null
        ),
        ItemDto(
            id = 2,
            name = "Adamantite Armor",
            classRestriction = null,
            level = 76,
            type = "ARMOR",
            rarity = "EPIC",
            location = "Adventurer's Forge",
            dropInfo = "Crafted with 50 Adamantite Ore",
            stats = mapOf("def" to 280, "hp" to 150, "mp" to 50),
            description = "Heavy armor forged from adamantite. Provides excellent protection against physical attacks.",
            iconUrl = null
        ),
        ItemDto(
            id = 3,
            name = "Health Potion",
            classRestriction = null,
            level = 1,
            type = "POTION",
            rarity = "COMMON",
            location = "All Merchants",
            dropInfo = "Available from all level 1+ merchants",
            stats = mapOf("heal" to 100),
            description = "Restores 100 HP instantly.",
            iconUrl = null
        ),
        ItemDto(
            id = 4,
            name = "Soul Shackle",
            classRestriction = "Archmage",
            level = 82,
            type = "WEAPON",
            rarity = "LEGENDARY",
            location = "Valakas Drop",
            dropInfo = "Dropped by Valakas, 10% chance",
            stats = mapOf("atk" to 380, "magicAtk" to 420, "mp" to 100),
            description = "A powerful staff imbued with dragon soul energy. Enhances magical attacks.",
            iconUrl = null
        ),
        ItemDto(
            id = 5,
            name = "Orfen's Shield",
            classRestriction = "Overlord",
            level = 78,
            type = "SHIELD",
            rarity = "RARE",
            location = "Orfen Drop",
            dropInfo = "Dropped by Orfen, 25% chance",
            stats = mapOf("def" to 200, "hp" to 100, "block" to 30),
            description = "A sturdy shield bearing the mark of Orfen. Blocks incoming attacks.",
            iconUrl = null
        ),
        ItemDto(
            id = 6,
            name = "Dusk Sun Blade",
            classRestriction = "Duelist",
            level = 85,
            type = "WEAPON",
            rarity = "DIVINE",
            location = "Queen Ant Drop",
            dropInfo = "Dropped by Queen Ant, 5% chance",
            stats = mapOf("atk" to 420, "critRate" to 20, "speed" to 150, "pvpAtk" to 50),
            description = "The most powerful blade known to mortals. Radiates with divine energy.",
            iconUrl = null
        ),
        ItemDto(
            id = 7,
            name = "Elven Robe",
            classRestriction = "Sorceress",
            level = 60,
            type = "ARMOR",
            rarity = "UNCOMMON",
            location = "Elven Forest Merchant",
            dropInfo = "Purchased from Elven Forest merchant",
            stats = mapOf("def" to 80, "mp" to 80, "magicDef" to 60),
            description = "Light armor worn by elven mages. Enhances magical defense.",
            iconUrl = null
        ),
        ItemDto(
            id = 8,
            name = "Scroll of Escape",
            classRestriction = null,
            level = 1,
            type = "SCROLL",
            rarity = "COMMON",
            location = "All Merchants",
            dropInfo = "Available from all level 1+ merchants",
            stats = mapOf(),
            description = "Teleports you to the nearest town.",
            iconUrl = null
        )
    )

    val quests = listOf(
        QuestDto(
            id = 101,
            name = "The Fall of Purgatory",
            npcId = 201,
            startLevel = 76,
            reward = listOf(RewardDto("EXP", 50000), RewardDto("ADENA", 100000)),
            description = "Defeat the forces of Purgatory and restore peace to the region. This quest involves battling through waves of enemies and defeating the final boss.",
            type = "MAIN",
            prerequisites = listOf(100),
            progressStatus = "NOT_STARTED"
        ),
        QuestDto(
            id = 102,
            name = "The Dragon's Breath",
            npcId = 202,
            startLevel = 80,
            reward = listOf(RewardDto("EXP", 75000), RewardDto("ADENA", 200000)),
            description = "Collect dragon breath samples from Antharas' lair. Be prepared for a dangerous encounter.",
            type = "SIDE",
            prerequisites = emptyList(),
            progressStatus = "NOT_STARTED"
        ),
        QuestDto(
            id = 103,
            name = "Arena Master",
            npcId = 203,
            startLevel = 70,
            reward = listOf(RewardDto("EXP", 30000), RewardDto("ADENA", 50000)),
            description = "Win 10 consecutive battles in the Arena to become the Arena Master.",
            type = "ARENA",
            prerequisites = emptyList(),
            progressStatus = "NOT_STARTED"
        ),
        QuestDto(
            id = 104,
            name = "The Lost Artifact",
            npcId = 204,
            startLevel = 65,
            reward = listOf(RewardDto("EXP", 25000), RewardDto("ITEM", 1)),
            description = "Find the lost artifact hidden in the ancient ruins. The artifact is guarded by powerful monsters.",
            type = "SIDE",
            prerequisites = emptyList(),
            progressStatus = "IN_PROGRESS"
        )
    )

    val skills = listOf(
        SkillDto(
            id = 301,
            name = "Soul Shot",
            classRestriction = "Warlord",
            level = 1,
            type = "ACTIVE",
            description = "Enhances your next attack with soul energy. Deals additional damage.",
            cooldown = 3,
            iconUrl = null,
            manaCost = 15,
            range = "SELF"
        ),
        SkillDto(
            id = 302,
            name = "Power Shock",
            classRestriction = "Warlord",
            level = 26,
            type = "ACTIVE",
            description = "Unleashes a powerful shock wave that damages all enemies in range.",
            cooldown = 10,
            iconUrl = null,
            manaCost = 45,
            range = "AREA"
        ),
        SkillDto(
            id = 303,
            name = "Dragon Slaying Technique",
            classRestriction = "Warlord",
            level = 40,
            type = "ACTIVE",
            description = "Increases damage against dragon-type monsters by 50%.",
            cooldown = null,
            iconUrl = null,
            manaCost = null,
            range = "PASSIVE"
        ),
        SkillDto(
            id = 304,
            name = "Fire Bolt",
            classRestriction = "Sorceress",
            level = 1,
            type = "ACTIVE",
            description = "Hurls a bolt of fire at the target. Basic magical attack.",
            cooldown = 2,
            iconUrl = null,
            manaCost = 8,
            range = "RANGED"
        ),
        SkillDto(
            id = 305,
            name = "Magic Amplify",
            classRestriction = "Sorceress",
            level = 10,
            type = "PASSIVE",
            description = "Increases magical attack power by 20%.",
            cooldown = null,
            iconUrl = null,
            manaCost = null,
            range = "PASSIVE"
        )
    )

    val classes = listOf(
        ClassDto(
            id = 401,
            name = "Warlord",
            race = "ORC",
            subClasses = listOf("Warlord", "Monk", "Paladin", "Dark Avenger"),
            description = "Master of melee combat and party buffs. Warlords excel in both damage dealing and tanking.",
            iconUrl = null,
            baseStats = mapOf("hp" to 150, "mp" to 40, "str" to 16, "dex" to 10, "con" to 16, "int" to 8, "wis" to 10, "cha" to 12)
        ),
        ClassDto(
            id = 402,
            name = "Sorceress",
            race = "HUMAN",
            subClasses = listOf("Sorceress", "Archmage", "Wizard", "Mystic"),
            description = "Powerful magical damage dealer. Sorceresses can devastate groups of enemies with area spells.",
            iconUrl = null,
            baseStats = mapOf("hp" to 80, "mp" to 80, "str" to 6, "dex" to 8, "con" to 8, "int" to 18, "wis" to 14, "cha" to 10)
        ),
        ClassDto(
            id = 403,
            name = "Duelist",
            race = "ELVEN",
            subClasses = listOf("Duelist", "Grand Knight", "Dark Knight", "Thunder Knight"),
            description = "Swift and agile warrior. Duelists specialize in critical hits and fast attacks.",
            iconUrl = null,
            baseStats = mapOf("hp" to 120, "mp" to 50, "str" to 14, "dex" to 16, "con" to 12, "int" to 8, "wis" to 10, "cha" to 14)
        ),
        ClassDto(
            id = 404,
            name = "Archmage",
            race = "DARK_ELF",
            subClasses = listOf("Archmage", "Sorceress", "Wizard", "Mystic"),
            description = "Ultimate magical warrior. Combines physical and magical combat prowess.",
            iconUrl = null,
            baseStats = mapOf("hp" to 100, "mp" to 70, "str" to 10, "dex" to 12, "con" to 10, "int" to 16, "wis" to 16, "cha" to 12)
        )
    )

    val npcs = listOf(
        NpcDto(
            id = 201,
            name = "Commander Varka",
            type = "QUEST_GIVER",
            location = "Valley of Saints",
            description = "Leader of the Varka Silenos tribe. Offers quests related to the Orc civilization.",
            relatedQuestIds = listOf(101, 102),
            iconUrl = null
        ),
        NpcDto(
            id = 202,
            name = "Merchant Golem",
            type = "MERCHANT",
            location = "Giran Harbor",
            description = "Sells potions, scrolls, and basic equipment. Available from dawn till dusk.",
            relatedQuestIds = emptyList(),
            iconUrl = null
        ),
        NpcDto(
            id = 203,
            name = "Antharas",
            type = "BOSS",
            location = "Tyr's Temple",
            description = "Ancient black dragon. One of the most powerful bosses in Lineage 2. Requires a full party to defeat.",
            relatedQuestIds = listOf(102),
            iconUrl = null
        ),
        NpcDto(
            id = 204,
            name = "Elder Brynhild",
            type = "TEACHER",
            location = "Elven Village",
            description = "Ancient elven elder who teaches advanced combat techniques to worthy students.",
            relatedQuestIds = listOf(104),
            iconUrl = null
        ),
        NpcDto(
            id = 205,
            name = "Arena Guard",
            type = "QUEST_GIVER",
            location = "Aden Castle",
            description = "Oversees the Arena battles. Allows players to compete in PvP combat.",
            relatedQuestIds = listOf(103),
            iconUrl = null
        )
    )
}
