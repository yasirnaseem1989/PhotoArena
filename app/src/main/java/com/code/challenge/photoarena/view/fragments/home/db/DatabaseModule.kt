package com.code.challenge.photoarena.view.fragments.home.db

import androidx.room.Room
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

internal const val FILENAME_PHOTOS_DB = "FILENAME_PHOTOS_DB"

val databaseModule = module {
    single {
        Room.databaseBuilder(
            androidApplication(),
            PhotoArenaDatabase::class.java,
            FILENAME_PHOTOS_DB
        )
            .addTypeConverter(get<PhotosConverter>())
            .fallbackToDestructiveMigration()
            .build()
    }

    single { get<PhotoArenaDatabase>().photosListDao() }

    factory { PhotosConverter() }
}