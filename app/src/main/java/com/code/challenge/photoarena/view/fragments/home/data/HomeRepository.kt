package com.code.challenge.photoarena.view.fragments.home.data

import com.code.challenge.photoarena.domain.Failure
import com.code.challenge.photoarena.domain.PhotosResult
import com.code.challenge.photoarena.view.fragments.home.domain.PhotoRequest
import com.code.challenge.photoarena.view.fragments.home.model.Photos

class HomeRepository(
    private val remoteHomeDataSource: HomeDataSource
) {

    suspend fun getPhotos(param: PhotoRequest): PhotosResult<Failure, List<Photos>> =
        remoteHomeDataSource.getPhotosList(param)
}

