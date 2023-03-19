package com.code.challenge.photoarena.domain

interface TimeoutSettings {
    fun getReadTimeout(): Long
    fun getWriteTimeout(): Long
    fun getConnectionTimeout(): Long
}

class DefaultTimeoutSettings : TimeoutSettings {

    companion object {
        private const val TIMEOUT_THRESHOLD = 60L
    }

    override fun getReadTimeout(): Long = TIMEOUT_THRESHOLD

    override fun getWriteTimeout(): Long = TIMEOUT_THRESHOLD

    override fun getConnectionTimeout(): Long = TIMEOUT_THRESHOLD
}