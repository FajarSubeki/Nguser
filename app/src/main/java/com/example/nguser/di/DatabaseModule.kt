package com.example.nguser.di

import android.app.Application
import androidx.room.Room
import com.example.nguser.data.local.UserDao
import com.example.nguser.data.local.UserDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(application: Application, callback: UserDatabase.Callback): UserDatabase{
        return Room.databaseBuilder(application, UserDatabase::class.java, "users_db")
            .fallbackToDestructiveMigration()
            .addCallback(callback)
            .build()
    }

    @Provides
    fun provideArticleDao(db: UserDatabase): UserDao{
        return db.getUserDao()
    }
}