package com.zx_tole.lineage2_guide.di

import com.zx_tole.lineage2_guide.data.local.Lineage2Database
import com.zx_tole.lineage2_guide.data.mapper.*
import com.zx_tole.lineage2_guide.data.network.Lineage2ApiService
import com.zx_tole.lineage2_guide.data.network.createHttpClient
import com.zx_tole.lineage2_guide.data.repository.*
import com.zx_tole.lineage2_guide.domain.repository.*
import com.zx_tole.lineage2_guide.domain.usecase.*
import org.koin.core.module.Module
import org.koin.dsl.module

fun sharedModule(baseUrl: String): List<Module> = listOf(
    networkModule(baseUrl),
    mapperModule(),
    repositoryModule(),
    useCaseModule()
)

fun networkModule(baseUrl: String): Module = module {
    single { createHttpClient(baseUrl) }
    single { Lineage2ApiService(get(), baseUrl) }
}

fun mapperModule(): Module = module {
    single { ItemMapper() }
    single { QuestMapper() }
    single { SkillMapper() }
    single { ClassMapper() }
    single { NpcMapper() }
}

fun repositoryModule(): Module = module {
    single { ItemsRepositoryImpl(get(), get()) }
    single { QuestsRepositoryImpl(get(), get()) }
    single { SkillsRepositoryImpl(get(), get()) }
    single { ClassesRepositoryImpl(get(), get()) }
    single { NpcsRepositoryImpl(get(), get()) }
}

fun useCaseModule(): Module = module {
    single { GetItemsUseCase(get()) }
    single { GetTotalCountUseCase(get()) }
    single { GetQuestsUseCase(get()) }
    single { GetSkillsUseCase(get()) }
    single { GetClassesUseCase(get()) }
    single { GetNpcsUseCase(get()) }
}
