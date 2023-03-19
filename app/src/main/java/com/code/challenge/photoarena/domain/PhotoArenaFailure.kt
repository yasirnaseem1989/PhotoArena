package com.code.challenge.photoarena.domain

import com.code.challenge.photoarena.utils.AppConstants.EMPTY_STRING


sealed class PhotoArenaFailure(val message: String?) : Failure.FeatureFailure()
data class EOFFailure(val errorMessage: String?) : PhotoArenaFailure(errorMessage)
data class NetworkFailure(val errorMessage: String?) : PhotoArenaFailure(errorMessage)
data class HttpFailure(val ex: Exception) : PhotoArenaFailure(ex.message)
object EmptyResponseFailure : PhotoArenaFailure(EMPTY_STRING)
object UnknownFailure : PhotoArenaFailure(EMPTY_STRING)
object NullResponseFailure : PhotoArenaFailure(EMPTY_STRING)
data class PhotoArenaServerFailure(val errorMessage: String?) : PhotoArenaFailure(errorMessage)