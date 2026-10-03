package com.zx_tole.lineage2_guide.server.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import java.io.BufferedReader
import java.io.InputStream
import java.util.zip.GZIPInputStream

@Serializable
data class RealItem(
    val id: Int,
    val name: String,
    val type: String,
    val grade: String?,
    val weight: Int?,
    val price: Int?,
    val material: String?,
    val bodypart: String?,
    val weaponType: String?,
    val armorType: String?,
    val pAtk: Double?,
    val mAtk: Double?,
    val pDef: Double?,
    val mDef: Double?,
    @SerialName("rCrit") val crit: Double?,
    @SerialName("pAtkSpd") val atkSpd: Int?,
    val crystalCount: Int?,
    val isMagical: Boolean?,
    val soulshots: Int?,
    val spiritshots: Int?,
    val crystalType: String? = null,
    val description: String? = null
)

@Serializable
data class RealSkill(
    val id: Int,
    val level: Int,
    val name: String,
    val operateType: String?,
    val magicLevel: Int?,
    val mpConsume: Int?,
    val castRange: Int?,
    val hitTime: Int?,
    val reuseDelay: Int?,
    val isMagic: Boolean?,
    val target: String?,
    val iconFile: String?,
    val description: String? = null,
    val power: Double? = null,
    val skillType: String?
)

@Serializable
data class RealNpc(
    val id: Int,
    val name: String,
    val level: Int?,
    val npcType: String?,
    val title: String?,
    val baseStats: Map<String, Double>? = emptyMap(),
    val hp: Double?,
    val mp: Double?,
    val pAtk: Double?,
    val pDef: Double?,
    val mDef: Double?,
    val mAtk: Double?,
    @SerialName("rCrit") val crit: Double? = null,
    val atkSpd: Int?,
    val runSpd: Int?,
    val walkSpd: Int?,
    val description: String? = null
)

@Serializable
data class RealQuest(
    val id: Int,
    val name: String,
    val levelMin: Int?,
    val startNpcIds: List<Int>?,
    val rewards: RealRewards?,
    val description: String? = null,
    val type: String? = null
)

@Serializable
data class RealRewards(
    val items: List<RealItemReward>?,
    val adena: Int?,
    val exp: Long?,
    val sp: Long?
)

@Serializable
data class RealItemReward(
    val itemId: Int,
    val count: Int
)

@Serializable
data class RealClass(
    val id: Int,
    val name: String,
    val race: String,
    val type: String?,
    val parentClassId: Int?,
    val skills: List<RealClassSkill>?,
    val description: String? = null
)

@Serializable
data class RealClassSkill(
    val skillId: Int,
    val skillLevel: Int,
    val minPlayerLevel: Int?,
    val spCost: Int?
)

object RealDataLoader {
    
    private fun sanitize(s: String?): String? {
        return s?.replace("\n", " ")?.replace("\r", " ")?.replace("\t", " ")?.replace(Regex("[\\x00-\\x1f]"), "")
    }
    
    private val json = Json {
        ignoreUnknownKeys = true
        coerceInputValues = true
    }
    
    private fun mapItem(real: RealItem): com.zx_tole.lineage2_guide.server.dto.ItemDto? {
        return try {
            val rarity = when (real.grade?.lowercase()) {
                "s" -> "Legendary"
                "a" -> "Epic"
                "b" -> "Rare"
                "c" -> "Uncommon"
                "d" -> "Common"
                "none", null -> "Common"
                else -> "Common"
            }
            
            val type = when {
                real.weaponType != null -> "Weapon"
                real.armorType != null -> "Armor"
                else -> "Consumable"
            }
            
            val stats = mutableMapOf<String, Int>()
            real.pAtk?.let { stats["pAtk"] = it.toInt() }
            real.mAtk?.let { stats["mAtk"] = it.toInt() }
            real.pDef?.let { stats["pDef"] = it.toInt() }
            real.mDef?.let { stats["mDef"] = it.toInt() }
            real.crit?.let { stats["crit"] = it.toInt() }
            real.atkSpd?.let { stats["atkSpd"] = it }
            real.crystalCount?.let { stats["crystalCount"] = it }
            
            com.zx_tole.lineage2_guide.server.dto.ItemDto(
                id = real.id.toLong(),
                name = real.name,
                classRestriction = null,
                level = 1,
                type = type,
                rarity = rarity,
                location = "Normal Drop",
                dropInfo = "Grade: ${real.grade ?: "none"}, Material: ${real.material}",
                stats = stats.map { com.zx_tole.lineage2_guide.server.dto.MapEntry(it.key, it.value) },
                description = sanitize(real.description),
                iconUrl = null
            )
        } catch (e: Exception) {
            null
        }
    }
    
    private fun mapSkill(real: RealSkill): com.zx_tole.lineage2_guide.server.dto.SkillDto? {
        return try {
            com.zx_tole.lineage2_guide.server.dto.SkillDto(
                id = real.id.toLong(),
                name = real.name,
                classRestriction = "All",
                level = real.level,
                type = when {
                    real.skillType?.contains("DAM") == true -> "Damage"
                    real.skillType?.contains("HEAL") == true -> "Heal"
                    real.skillType?.contains("BUFF") == true -> "Buff"
                    real.skillType?.contains("DEBUFF") == true -> "Debuff"
                    real.operateType?.contains("AREA") == true -> "Area"
                    else -> "Normal"
                },
                description = sanitize(real.description),
                cooldown = real.reuseDelay,
                iconUrl = real.iconFile,
                manaCost = real.mpConsume,
                range = real.castRange?.toString()
            )
        } catch (e: Exception) {
            null
        }
    }
    
    private fun mapNpc(real: RealNpc): com.zx_tole.lineage2_guide.server.dto.NpcDto? {
        return try {
            val type = when (real.npcType) {
                "L2Monster" -> "Monster"
                "L2PcInstance" -> "Player"
                "L2Npc" -> "Quest Giver"
                "L2Buyer" -> "Vendor"
                "L2PolyInstance" -> "Boss"
                else -> "Other"
            }
            
            com.zx_tole.lineage2_guide.server.dto.NpcDto(
                id = real.id.toLong(),
                name = real.name.ifBlank { "Unknown NPC" },
                type = type,
                location = "Unknown",
                description = sanitize(real.description),
                relatedQuestIds = emptyList(),
                iconUrl = null
            )
        } catch (e: Exception) {
            null
        }
    }
    
    private fun mapQuest(real: RealQuest): com.zx_tole.lineage2_guide.server.dto.QuestDto? {
        return try {
            val reward = mutableListOf<com.zx_tole.lineage2_guide.server.dto.RewardDto>()
            real.rewards?.exp?.let { reward.add(com.zx_tole.lineage2_guide.server.dto.RewardDto("exp", it.toInt())) }
            real.rewards?.sp?.let { reward.add(com.zx_tole.lineage2_guide.server.dto.RewardDto("sp", it.toInt())) }
            real.rewards?.adena?.let { reward.add(com.zx_tole.lineage2_guide.server.dto.RewardDto("adena", it)) }
            real.rewards?.items?.forEach { itemReward ->
                reward.add(com.zx_tole.lineage2_guide.server.dto.RewardDto("item_${itemReward.itemId}", itemReward.count))
            }
            
            com.zx_tole.lineage2_guide.server.dto.QuestDto(
                id = real.id.toLong(),
                name = real.name,
                npcId = real.startNpcIds?.firstOrNull()?.toLong(),
                startLevel = real.levelMin ?: 1,
                reward = reward,
                description = sanitize(real.description),
                type = "Main",
                prerequisites = emptyList(),
                progressStatus = null
            )
        } catch (e: Exception) {
            null
        }
    }
    
    private fun mapClass(real: RealClass): com.zx_tole.lineage2_guide.server.dto.ClassDto? {
        return try {
            val subClasses = if (real.parentClassId != null) {
                listOf("Parent: ID ${real.parentClassId}")
            } else {
                emptyList()
            }
            
            com.zx_tole.lineage2_guide.server.dto.ClassDto(
                id = real.id.toLong(),
                name = real.name,
                race = real.race,
                subClasses = subClasses,
                description = sanitize(real.description),
                iconUrl = null,
                baseStats = emptyList()
            )
        } catch (e: Exception) {
            null
        }
    }
    
    fun loadItems(): List<com.zx_tole.lineage2_guide.server.dto.ItemDto> {
        try {
            val inputStream = RealDataLoader::class.java.classLoader
                .getResourceAsStream("items.json.gz") ?: return emptyList()
            
            val gzipStream = GZIPInputStream(inputStream)
            val jsonString = BufferedReader(gzipStream.bufferedReader()).use { it.readText() }
            gzipStream.close()
            
            val realItems = json.decodeFromString<List<RealItem>>(jsonString)
            
            return realItems.mapNotNull { mapItem(it) }
        } catch (e: Exception) {
            e.printStackTrace()
            return emptyList()
        }
    }
    
    fun loadSkills(): List<com.zx_tole.lineage2_guide.server.dto.SkillDto> {
        try {
            val inputStream = RealDataLoader::class.java.classLoader
                .getResourceAsStream("skills.json.gz") ?: return emptyList()
            
            val gzipStream = GZIPInputStream(inputStream)
            val jsonString = BufferedReader(gzipStream.bufferedReader()).use { it.readText() }
            gzipStream.close()
            
            val realSkills = json.decodeFromString<List<RealSkill>>(jsonString)
            
            return realSkills.mapNotNull { mapSkill(it) }
        } catch (e: Exception) {
            e.printStackTrace()
            return emptyList()
        }
    }
    
    fun loadNpcs(): List<com.zx_tole.lineage2_guide.server.dto.NpcDto> {
        try {
            val inputStream = RealDataLoader::class.java.classLoader
                .getResourceAsStream("npcs.json.gz") ?: return emptyList()
            
            val gzipStream = GZIPInputStream(inputStream)
            val jsonString = BufferedReader(gzipStream.bufferedReader()).use { it.readText() }
            gzipStream.close()
            
            val realNpcs = json.decodeFromString<List<RealNpc>>(jsonString)
            
            return realNpcs.mapNotNull { mapNpc(it) }
        } catch (e: Exception) {
            e.printStackTrace()
            return emptyList()
        }
    }
    
    fun loadQuests(): List<com.zx_tole.lineage2_guide.server.dto.QuestDto> {
        try {
            val inputStream = RealDataLoader::class.java.classLoader
                .getResourceAsStream("quests.json.gz") ?: return emptyList()
            
            val gzipStream = GZIPInputStream(inputStream)
            val jsonString = BufferedReader(gzipStream.bufferedReader()).use { it.readText() }
            gzipStream.close()
            
            val realQuests = json.decodeFromString<List<RealQuest>>(jsonString)
            
            return realQuests.mapNotNull { mapQuest(it) }
        } catch (e: Exception) {
            e.printStackTrace()
            return emptyList()
        }
    }
    
    fun loadClasses(): List<com.zx_tole.lineage2_guide.server.dto.ClassDto> {
        try {
            val inputStream = RealDataLoader::class.java.classLoader
                .getResourceAsStream("classes.json.gz") ?: return emptyList()
            
            val gzipStream = GZIPInputStream(inputStream)
            val jsonString = BufferedReader(gzipStream.bufferedReader()).use { it.readText() }
            gzipStream.close()
            
            val realClasses = json.decodeFromString<List<RealClass>>(jsonString)
            
            return realClasses.mapNotNull { mapClass(it) }
        } catch (e: Exception) {
            e.printStackTrace()
            return emptyList()
        }
    }
}
