package com.code.challenge.photoarena.view.fragments.home.db

import android.app.Application
import androidx.room.Room
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

internal const val FILENAME_PHOTOS_DB = "FILENAME_PHOTOS_DB"

val databaseModule = module {

    fun provideDatabase(application: Application): PhotoArenaDatabase {
        return Room.databaseBuilder(application, PhotoArenaDatabase::class.java, FILENAME_PHOTOS_DB)
            .fallbackToDestructiveMigration().build()
    }

    fun providerPhotosDao(database: PhotoArenaDatabase): PhotosDao {
        return database.photosListDao
    }

    single { provideDatabase(androidApplication()) }

    single { providerPhotosDao(get()) }
}