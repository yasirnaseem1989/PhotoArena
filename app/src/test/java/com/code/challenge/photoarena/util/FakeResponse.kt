package com.code.challenge.photoarena.util

import com.code.challenge.photoarena.view.fragments.home.model.Photos

object FakeResponse {

    fun getPhotosSuccess() = listOf(
        Photos(
            id = 1,
            name = "JillWellington",
            thumbnailUrl = "https://cdn.pixabay.com/photo/2017/05/08/13/15/spring-bird-2295434_150.jpg",
            imageURL = "https://pixabay.com/get/ga9727a463cafeb8e7a204537618a0231d16edc4b9281fd7c97684e069735848e9eb179e1a2ddaaa1e9dd97053223553ab245cd9c7cd38ba16d0d69e1f386e312_1280.jpg",
            tags = "spring bird, bird, tit"
        ),
        Photos(
            id = 2,
            name = "Allen",
            thumbnailUrl = "https://cdn.pixabay.com/photo/2017/05/08/13/15/spring-bird-2295434_150.jpg",
            imageURL = "https://pixabay.com/get/ga9727a463cafeb8e7a204537618a0231d16edc4b9281fd7c97684e069735848e9eb179e1a2ddaaa1e9dd97053223553ab245cd9c7cd38ba16d0d69e1f386e312_1280.jpg",
            tags = "spring bird, bird, tit"
        ),
        Photos(
            id = 3,
            name = "Shubana",
            thumbnailUrl = "https://cdn.pixabay.com/photo/2017/05/08/13/15/spring-bird-2295434_150.jpg",
            imageURL = "https://pixabay.com/get/ga9727a463cafeb8e7a204537618a0231d16edc4b9281fd7c97684e069735848e9eb179e1a2ddaaa1e9dd97053223553ab245cd9c7cd38ba16d0d69e1f386e312_1280.jpg",
            tags = "spring bird, bird, tit"
        )
    )
}

