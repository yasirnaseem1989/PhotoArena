package com.code.challenge.photoarena.domain

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import timber.log.Timber

abstract class BaseUseCase<in Params, out Type>(private val coroutineDispatcher: CoroutineDispatcher) where Type : Any {

    abstract suspend fun execute(params: Params): PhotosResult<Failure, Type>

    protected open suspend fun handleError(ex: Exception): Failure = FailureFactory.create(ex)

    suspend operator fun invoke(params: Params, onResult: (PhotosResult<Failure, Type>) -> Unit) {
        withContext(coroutineDispatcher) {
            try {
                val result = execute(params).also { log(it) }
                onResult.invoke(result)
            } catch (ex: Exception) {
                Timber.e(ex)
                val failure = handleError(ex)
                onResult.invoke(PhotosResult.Error(failure))
            }
        }
    }

    private fun log(photosResult: PhotosResult<Failure, Type>) {
        Timber.tag(TAG).d("${this.javaClass.simpleName}=$photosResult")
    }

    private companion object {
        private const val TAG = "USECASE_RESULT"
    }
}

suspend operator fun <R : Any> BaseUseCase<Unit, R>.invoke(onResult: (PhotosResult<Failure, R>) -> Unit) =
    this(Unit, onResult)
