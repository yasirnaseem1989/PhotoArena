package com.code.challenge.photoarena.view.fragments.home.model

data class Photos(
    val id: Int = Int.MAX_VALUE,
    val name: String = "",
    val thumbnailUrl: String = "",
    val imageURL: String = "",
    val tags: String = "",
    val totalDownloads: String = "",
    val totalLikes: String = "",
    val totalComments: String = ""
)
