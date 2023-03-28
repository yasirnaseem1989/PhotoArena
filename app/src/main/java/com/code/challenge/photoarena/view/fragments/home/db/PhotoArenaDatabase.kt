package com.code.challenge.photoarena.view.fragments.home.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.code.challenge.photoarena.view.fragments.home.model.Photos

@Database(
    entities = [Photos::class], version = 1, exportSchema = false
)
@TypeConverters(PhotosConverter::class)

abstract class PhotoArenaDatabase : RoomDatabase() {
    abstract val photosListDao: PhotosDao
}