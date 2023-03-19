package com.code.challenge.photoarena.view.fragments.home.data

import com.code.challenge.photoarena.domain.Failure
import com.code.challenge.photoarena.domain.PhotosResult
import com.code.challenge.photoarena.view.fragments.home.domain.PhotoRequest
import com.code.challenge.photoarena.view.fragments.home.model.Photos

interface HomeDataSource {

    suspend fun getPhotosList(param: PhotoRequest): PhotosResult<Failure, List<Photos>>
}