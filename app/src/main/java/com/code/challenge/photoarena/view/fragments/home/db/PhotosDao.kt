package com.code.challenge.photoarena.view.fragments.home.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.code.challenge.photoarena.view.fragments.home.model.Photos

@Dao
interface PhotosDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllPhotos(photos: List<Photos>)

    @Query("SELECT * FROM favorite_photos")
    fun getAllPhotos(): List<Photos>

}