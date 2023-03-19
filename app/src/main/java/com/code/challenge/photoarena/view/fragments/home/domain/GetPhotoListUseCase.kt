package com.code.challenge.photoarena.view.fragments.home.domain

import com.code.challenge.photoarena.domain.BaseUseCase
import com.code.challenge.photoarena.domain.Failure
import com.code.challenge.photoarena.domain.Failure.FeatureFailure
import com.code.challenge.photoarena.domain.PhotosResult
import com.code.challenge.photoarena.domain.PhotosResult.Error
import com.code.challenge.photoarena.view.fragments.home.data.HomeRepository
import com.code.challenge.photoarena.view.fragments.home.domain.PhotosFailure.NoPhotosFailure
import com.code.challenge.photoarena.view.fragments.home.model.Photos
import kotlinx.coroutines.CoroutineDispatcher

class GetPhotoListUseCase(
    private val homeRepository: HomeRepository,
    ioDispatcher: CoroutineDispatcher
) : BaseUseCase<PhotoRequest, List<Photos>>(ioDispatcher) {

    override suspend fun execute(params: PhotoRequest): PhotosResult<Failure, List<Photos>> {
        if (params.query.isEmpty()) {
            Error(NoPhotosFailure)
        }

        return homeRepository.getPhotos(params)
    }

    companion object {
        const val QUERY_DEFAULT = "fruits"
    }
}

data class PhotoRequest(
    val query: String = ""
)

sealed class PhotosFailure : FeatureFailure() {
    object NoPhotosFailure : PhotosFailure()
}