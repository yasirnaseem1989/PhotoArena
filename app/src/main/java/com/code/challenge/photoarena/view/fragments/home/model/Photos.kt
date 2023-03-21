package com.code.challenge.photoarena.view.fragments.home.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_photos")
data class Photos(
    @PrimaryKey(autoGenerate = false) val id: Int = Int.MAX_VALUE,
    val name: String = "",
    val thumbnailUrl: String = "",
    val imageURL: String = "",
    val tags: String = "",
    val totalDownloads: String = "",
    val totalLikes: String = "",
    val totalComments: String = ""
)
