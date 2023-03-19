package com.code.challenge.photoarena.view.fragments.home.data

import com.code.challenge.photoarena.utils.AppConstants
import com.code.challenge.photoarena.view.fragments.home.model.RemotePhotoResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface HomeService {

    @GET("api/")
    suspend fun getProductsList(
        @Query("key") key: String = AppConstants.KEY.API_KEY,
        @Query("q") query: String,
        @Query("image_type") imageType: String = "photo"
    ):
        RemotePhotoResponse
}