package com.code.challenge.photoarena.domain

import androidx.annotation.Keep

@Keep
sealed class Failure {
    object UnknownFailure : Failure()
    object NetworkConnection : Failure()
    object ServerError : Failure()

    abstract class FeatureFailure : Failure()

    override fun toString(): String {
        return this.javaClass.simpleName
    }
}