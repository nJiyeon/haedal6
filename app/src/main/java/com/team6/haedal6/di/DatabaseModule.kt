package com.team6.haedal6.di


import android.content.Context
import androidx.room.Room
import com.team6.haedal6.database.AppDatabase
import com.team6.haedal6.repository.keyword.KeywordDao
import com.team6.haedal6.repository.keyword.KeywordRepository
import com.team6.haedal6.repository.location.LocationDao
import com.team6.haedal6.repository.location.LocationSearcher
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "app_database"
        )
            .fallbackToDestructiveMigration() // 필요시 마이그레이션 재생성
            .build()
    }

    @Provides
    @Singleton
    fun provideKeywordDao(database: AppDatabase): KeywordDao {
        return database.keywordDao()
    }

    @Provides
    @Singleton
    fun provideItemDao(database: AppDatabase): LocationDao {
        return database.locationDao()
    }

    @Provides
    @Singleton
    fun provideKeywordRepository(keywordDao: KeywordDao): KeywordRepository {
        return KeywordRepository(keywordDao)
    }

    @Provides
    @Singleton
    fun provideLocationSearcher(locationDao: LocationDao): LocationSearcher {
        return LocationSearcher(locationDao)
    }
}
