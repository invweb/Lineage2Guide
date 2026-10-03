package com.zx_tole.lineage2_guide.di

import com.zx_tole.lineage2_guide.BuildConfig
import com.zx_tole.lineage2_guide.data.local.Lineage2Database
import com.zx_tole.lineage2_guide.data.local.dao.*
import com.zx_tole.lineage2_guide.data.mapper.*
import com.zx_tole.lineage2_guide.data.network.Lineage2ApiService
import com.zx_tole.lineage2_guide.data.repository.*
import com.zx_tole.lineage2_guide.domain.repository.*
import com.zx_tole.lineage2_guide.domain.usecase.*
import com.zx_tole.lineage2_guide.ui.items.ItemsViewModel
import com.zx_tole.lineage2_guide.ui.quests.QuestsViewModel
import com.zx_tole.lineage2_guide.ui.skills.SkillsViewModel
import com.zx_tole.lineage2_guide.ui.classes.ClassesViewModel
import com.zx_tole.lineage2_guide.ui.npcs.NpcsViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

fun appModule(baseUrl: String = BuildConfig.API_BASE_URL): List<Module> = listOf(
    networkModule(baseUrl),
    databaseModule(),
    mapperModule(),
    repositoryModule(),
    useCaseModule(),
    viewModelModule()
)

fun networkModule(baseUrl: String = BuildConfig.API_BASE_URL): Module = module {
    single {
        com.squareup.moshi.Moshi.Builder()
            .add(com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory())
            .build()
    }

    single {
        okhttp3.OkHttpClient.Builder()
            .connectTimeout(30, java.util.concurrent.TimeUnit.SECONDS)
            .readTimeout(30, java.util.concurrent.TimeUnit.SECONDS)
            .writeTimeout(30, java.util.concurrent.TimeUnit.SECONDS)
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .header("Content-Type", "application/json")
                    .build()
                chain.proceed(request)
            }
            .build()
    }

    single {
        retrofit2.Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(get())
            .addConverterFactory(retrofit2.converter.moshi.MoshiConverterFactory.create(get()))
            .build()
    }

    single<Lineage2ApiService> { get<retrofit2.Retrofit>().create(Lineage2ApiService::class.java) }
}

fun databaseModule(): Module = module {
    single {
        androidx.room.Room.databaseBuilder(
            androidApplication(),
            Lineage2Database::class.java,
            "lineage2_guide.db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    single { get<Lineage2Database>().itemsDao() }
    single { get<Lineage2Database>().questsDao() }
    single { get<Lineage2Database>().skillsDao() }
    single { get<Lineage2Database>().classesDao() }
    single { get<Lineage2Database>().npcsDao() }
}

fun mapperModule(): Module = module {
    single { ItemMapper() }
    single { QuestMapper() }
    single { SkillMapper() }
    single { ClassMapper() }
    single { NpcMapper() }
}

fun repositoryModule(): Module = module {
    singleOf(::ItemsRepositoryImpl) { bind<ItemsRepository>() }
    singleOf(::QuestsRepositoryImpl) { bind<QuestsRepository>() }
    singleOf(::SkillsRepositoryImpl) { bind<SkillsRepository>() }
    singleOf(::ClassesRepositoryImpl) { bind<ClassesRepository>() }
    singleOf(::NpcsRepositoryImpl) { bind<NpcsRepository>() }
}

fun useCaseModule(): Module = module {
    single { GetItemsUseCase(get()) }
    single { GetTotalCountUseCase(get()) }
    single { GetQuestsUseCase(get()) }
    single { GetSkillsUseCase(get()) }
    single { GetClassesUseCase(get()) }
    single { GetNpcsUseCase(get()) }
}

fun viewModelModule(): Module = module {
    viewModel { ItemsViewModel(get(), get()) }
    viewModel { QuestsViewModel(get()) }
    viewModel { SkillsViewModel(get()) }
    viewModel { ClassesViewModel(get()) }
    viewModel { NpcsViewModel(get()) }
}
