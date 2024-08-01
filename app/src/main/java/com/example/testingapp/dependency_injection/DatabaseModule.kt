package com.example.testingapp.dependency_injection

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Provides
    @Singleton
    fun getDatabase(@ApplicationContext context: Context): RoomDatabase {
        return Room.databaseBuilder(context, RoomDatabase::class.java,"quotes_database").build()
    }
}