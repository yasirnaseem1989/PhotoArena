package com.code.challenge.photoarena.view.fragments.home.data

import com.code.challenge.photoarena.ext.orZero
import com.code.challenge.photoarena.view.fragments.home.model.Photos
import com.code.challenge.photoarena.view.fragments.home.model.RemotePhotos

class PhotosMapper(private val homeResourceProvider: HomeResourceProvider) {

    fun map(remotePhotos: RemotePhotos): Photos {

        return Photos(
            id = remotePhotos.id.orZero(),
            name = remotePhotos.user.orEmpty(),
            thumbnailUrl = remotePhotos.previewURL.orEmpty(),
            imageURL = remotePhotos.largeImageURL.orEmpty(),
            tags = remotePhotos.tags.orEmpty(),
            totalDownloads = homeResourceProvider.getTotalDownloadText(remotePhotos.downloads.orZero()),
            totalLikes = homeResourceProvider.getTotalLikesText(remotePhotos.likes.orZero()),
            totalComments = homeResourceProvider.getTotalCommentsText(remotePhotos.comments.orZero())
        )
    }
}