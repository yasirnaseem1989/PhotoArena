package com.code.challenge.photoarena.view.fragments.home.db

import androidx.room.TypeConverters
import com.code.challenge.photoarena.view.fragments.home.model.Photos
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class PhotosConverter {

    @TypeConverters
    fun stringToPhotos(json: String): List<Photos> {
        val type = object : TypeToken<List<Photos>>() {}.type
        return Gson().fromJson(json, type)
    }

    @TypeConverters
    fun photosToString(photo: Photos): String {
        val type = object : TypeToken<List<Photos>>() {}.type
        return Gson().toJson(photo, type)
    }
}