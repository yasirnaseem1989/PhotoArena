package com.code.challenge.photoarena.view.fragments.home.data

import com.code.challenge.photoarena.domain.Failure
import com.code.challenge.photoarena.domain.PhotosResult
import com.code.challenge.photoarena.view.fragments.home.model.Photos

interface HomeLocalDataSource {

    suspend fun insertAllPhotos(photos: List<Photos>)

    suspend fun getPhotoList(): PhotosResult<Failure, List<Photos>>
}