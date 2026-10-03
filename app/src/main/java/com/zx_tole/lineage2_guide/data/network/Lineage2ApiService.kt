package com.zx_tole.lineage2_guide.data.network

import com.zx_tole.lineage2_guide.data.network.dto.*
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Lineage2ApiService {

    @GET("items")
    suspend fun getItems(
        @Query("classRestriction") classRestriction: String? = null,
        @Query("minLevel") minLevel: Int? = null,
        @Query("maxLevel") maxLevel: Int? = null,
        @Query("type") type: String? = null,
        @Query("rarity") rarity: String? = null,
        @Query("location") location: String? = null,
        @Query("search") search: String? = null,
        @Query("page") page: Int = 0,
        @Query("pageSize") pageSize: Int = 20
    ): ItemsResponse

    @GET("items/{id}")
    suspend fun getItemById(@Path("id") id: Long): ItemResponse

    @GET("quests")
    suspend fun getQuests(
        @Query("type") type: String? = null,
        @Query("startLevel") startLevel: Int? = null,
        @Query("npcId") npcId: Long? = null
    ): QuestsResponse

    @GET("skills")
    suspend fun getSkills(
        @Query("classRestriction") classRestriction: String? = null,
        @Query("type") type: String? = null,
        @Query("minLevel") minLevel: Int? = null,
        @Query("maxLevel") maxLevel: Int? = null,
        @Query("search") search: String? = null
    ): SkillsResponse

    @GET("classes")
    suspend fun getClasses(
        @Query("race") race: String? = null,
        @Query("search") search: String? = null
    ): ClassesResponse

    @GET("npcs")
    suspend fun getNpcs(
        @Query("type") type: String? = null,
        @Query("location") location: String? = null,
        @Query("search") search: String? = null
    ): NpcsResponse

    @GET("sync")
    suspend fun getSyncData(
        @Query("lastSync") lastSyncMs: Long = 0
    ): SyncResponse
}
