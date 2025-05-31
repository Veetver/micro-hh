package ru.practicum.android.microhh.core.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    version = 1,
    entities = [JobInfoEntity::class]
)
abstract class AppDataBase : RoomDatabase() {
    abstract fun favoriteJobDao(): JobInfoDao
}
