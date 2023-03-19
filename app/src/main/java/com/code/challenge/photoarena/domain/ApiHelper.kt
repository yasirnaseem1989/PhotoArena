package com.code.challenge.photoarena.domain

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Protocol
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import org.json.JSONException
import org.json.JSONObject
import retrofit2.HttpException
import timber.log.Timber
import java.io.EOFException
import java.io.IOException

fun handleError(ex: Exception): PhotoArenaFailure =
    when (ex) {
        is EOFException -> EOFFailure(ex.message)
        is IOException -> NetworkFailure(ex.message)
        is HttpException -> HttpFailure(convertToLocalizedException(ex))
        else -> UnknownFailure
    }

inline fun <reified T> invokeApiCall(apiCall: () -> T): PhotosResult<PhotoArenaFailure, T> {
    return try {
        val response = apiCall.invoke()
        return PhotosResult.Success(response)
    } catch (ex: Exception) {
        Timber.e(ex)
        val failure = handleError(ex)
        PhotosResult.Error(failure)
    }
}

fun convertToLocalizedException(exception: HttpException): Exception {
    val errorBody = exception.response()?.errorBody()
    return if (errorBody != null) {
        return try {
            val rawBody = errorBody.string()

            val message = JSONObject(rawBody).getString("message")
            val statusCode = JSONObject(rawBody).getString("statusCode")

            val response: Response = Response.Builder()
                .body(null)
                .code(statusCode.toInt())
                .message(message)
                .protocol(Protocol.HTTP_1_1)
                .request(Request.Builder().url("http://localhost/").build())
                .build()

            CustomHttpException(
                retrofit2.Response.error<String>(
                    message.toResponseBody("plain/text".toMediaTypeOrNull()),
                    response
                )
            )

        } catch (e: JSONException) {
            return e
        }
    } else {
        exception
    }
}

class CustomHttpException(response: retrofit2.Response<*>) : HttpException(response) {

    override val message: String?
        get() = message()

    override fun getLocalizedMessage(): String? {
        return message()
    }
}