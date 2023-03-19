package com.code.challenge.photoarena.domain

import com.code.challenge.photoarena.domain.PhotosResult.Error
import com.code.challenge.photoarena.domain.PhotosResult.Success


sealed class PhotosResult<out Failure, out Data> {

    data class Error<out Failure>(val errorData: Failure) : PhotosResult<Failure, Nothing>()
    data class Success<out Data>(val data: Data) : PhotosResult<Nothing, Data>()

    val isSuccess get() = this is Success<Data>
    val isError get() = this is Error<Failure>

    fun result(onError: (Failure) -> Unit = {}, onSuccess: (Data) -> Unit = {}): Any =
        when (this) {
            is Error -> onError(errorData)
            is Success -> onSuccess(data)
        }
}

fun <OldType, Failure, NewType> PhotosResult<Failure, OldType>.map(
    transform: (OldType) -> NewType
): PhotosResult<Failure, NewType> {
    return when (this) {
        is Success -> Success(transform(data))
        is Error -> this
    }
}

fun <OldType, Failure, NewType> PhotosResult<Failure, OldType>.flatMap(
    transform: (OldType) -> PhotosResult<Failure, NewType>
): PhotosResult<Failure, NewType> {
    return when (this) {
        is Success -> transform(data)
        is Error -> this
    }
}
