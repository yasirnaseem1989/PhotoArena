package com.code.challenge.photoarena.domain


import com.code.challenge.photoarena.domain.Failure.NetworkConnection
import com.code.challenge.photoarena.domain.Failure.UnknownFailure
import timber.log.Timber
import java.net.UnknownHostException

object FailureFactory {

    fun create(ex: Throwable): Failure {
        val failure = when (ex) {
            is UnknownHostException -> NetworkConnection
            else -> UnknownFailure
        }
        Timber.d("[${ex.javaClass.simpleName}] -> [$failure]")
        return failure
    }
}
