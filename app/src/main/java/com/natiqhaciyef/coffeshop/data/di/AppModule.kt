package com.natiqhaciyef.coffeshop.data.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.natiqhaciyef.coffeshop.data.repository.AppRepository
import com.natiqhaciyef.coffeshop.data.roomdb.AppDao
import com.natiqhaciyef.coffeshop.data.roomdb.AppDatabase
import com.natiqhaciyef.coffeshop.data.source.AppDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDao(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, AppDatabase::class.java, "app_database")
            .fallbackToDestructiveMigration()
            .build()
            .getDao()

    @Provides
    @Singleton
    fun provideAppDataSource(dao: AppDao) = AppDataSource(dao)

    @Provides
    @Singleton
    fun provideAppRepository(ds: AppDataSource) = AppRepository(ds)
}