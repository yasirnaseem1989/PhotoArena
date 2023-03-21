package com.code.challenge.photoarena.view.fragments.home.data

import com.code.challenge.photoarena.domain.Failure
import com.code.challenge.photoarena.domain.PhotosResult
import com.code.challenge.photoarena.domain.PhotosResult.Success
import com.code.challenge.photoarena.view.fragments.home.db.PhotosDao
import com.code.challenge.photoarena.view.fragments.home.model.Photos

class LocalPhotosDataSource(
    private val photoDao: PhotosDao,
) : HomeLocalDataSource {

    override suspend fun getPhotoList(): PhotosResult<Failure, List<Photos>> {
       return Success(photoDao.getAllPhotos())
    }

    override suspend fun insertAllPhotos(photos: List<Photos>) {
        photoDao.insertAllPhotos(photos)
    }
}