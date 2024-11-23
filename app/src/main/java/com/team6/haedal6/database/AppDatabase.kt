package com.team6.haedal6.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.team6.haedal6.entity.KeywordEntity
import com.team6.haedal6.entity.LocationEntity
import com.team6.haedal6.repository.keyword.KeywordDao
import com.team6.haedal6.repository.location.LocationDao

@Database(entities = [KeywordEntity::class, LocationEntity::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun keywordDao(): KeywordDao
    abstract fun locationDao(): LocationDao
}