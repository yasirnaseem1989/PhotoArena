package com.code.challenge.photoarena.view.fragments.home.data

import com.code.challenge.photoarena.domain.Failure
import com.code.challenge.photoarena.domain.PhotosResult
import com.code.challenge.photoarena.domain.PhotosResult.Error
import com.code.challenge.photoarena.domain.PhotosResult.Success
import com.code.challenge.photoarena.domain.flatMap
import com.code.challenge.photoarena.domain.invokeApiCall
import com.code.challenge.photoarena.view.fragments.home.domain.PhotoRequest
import com.code.challenge.photoarena.view.fragments.home.domain.PhotosFailure.NoPhotosFailure
import com.code.challenge.photoarena.view.fragments.home.model.Photos

class RemoteHomeDateSource(
    private val homeService: HomeService,
    private val photosMapper: PhotosMapper
) : HomeDataSource {

    override suspend fun getPhotosList(param: PhotoRequest): PhotosResult<Failure, List<Photos>> {
        return invokeApiCall {
            with(param) {
                homeService.getProductsList(query = query)
            }
        }.flatMap { response ->
            if (response.hits.isEmpty()) {
                Error(NoPhotosFailure)
            } else {
                val photos = response.hits.map {
                    photosMapper.map(it)
                }
                Success(photos)
            }
        }
    }
}