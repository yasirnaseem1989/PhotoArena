package com.code.challenge.photoarena.domain

import java.io.IOException

class NoConnectivityException : IOException() {
    override val message: String
        get() = "No internet available, please check your WiFi or Data connection"
}

class NoInternetException : IOException() {
    override val message: String
        get() = "No internet available, please check your WiFi or Data connection"
}

class NoResponseBodyException : IOException() {
    override val message: String
        get() = "Error parsing response"
}
