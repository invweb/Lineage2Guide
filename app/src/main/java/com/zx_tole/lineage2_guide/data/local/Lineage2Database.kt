package com.zx_tole.lineage2_guide.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.zx_tole.lineage2_guide.data.local.dao.*
import com.zx_tole.lineage2_guide.data.local.entity.*

@Database(
    entities = [
        ItemEntity::class,
        QuestEntity::class,
        SkillEntity::class,
        ClassEntity::class,
        NpcEntity::class
    ],
    version = 1,
    exportSchema = true
)
abstract class Lineage2Database : RoomDatabase() {

    abstract fun itemsDao(): ItemsDao
    abstract fun questsDao(): QuestsDao
    abstract fun skillsDao(): SkillsDao
    abstract fun classesDao(): ClassesDao
    abstract fun npcsDao(): NpcsDao

    companion object {
        @Volatile
        private var INSTANCE: Lineage2Database? = null

        fun getInstance(context: Context): Lineage2Database {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    Lineage2Database::class.java,
                    "lineage2_guide.db"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
