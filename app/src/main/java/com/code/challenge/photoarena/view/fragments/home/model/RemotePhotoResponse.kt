package com.code.challenge.photoarena.view.fragments.home.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RemotePhotoResponse(
    val total: Int? = null,
    val totalHits: Int? = null,
    val hits: List<RemotePhotos> = emptyList()
)

@JsonClass(generateAdapter = true)
data class RemotePhotos(
    val id: Int? = null,
    val user: String? = null,
    val previewURL: String? = null,
    val largeImageURL: String? = null,
    val tags: String? = null,
    val downloads: Int? = null,
    val likes: Int? = null,
    val comments: Int? = null,
)


