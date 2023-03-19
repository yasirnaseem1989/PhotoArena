package com.code.challenge.photoarena.di

import com.code.challenge.photoarena.BuildConfig
import com.code.challenge.photoarena.domain.DefaultTimeoutSettings
import com.code.challenge.photoarena.domain.NoConnectionInterceptor
import com.code.challenge.photoarena.domain.TimeoutSettings
import com.code.challenge.photoarena.view.fragments.home.data.HomeService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

internal const val INTERCEPTOR_NO_CONNECTION = "INTERCEPTOR_NO_CONNECTION"
internal const val INTERCEPTOR_HTTP_REQUESTS = "INTERCEPTOR_HTTP_REQUESTS"
internal const val OKHTTP_FOR_HOME = "OKHTTP_FOR_HOME"
internal const val RETROFIT_HOME = "RETROFIT_HOME"

val networkModule = module {

    factory { Moshi.Builder().add(KotlinJsonAdapterFactory()).build() }
    factory<TimeoutSettings> { DefaultTimeoutSettings() }
    factory<Interceptor>(named(INTERCEPTOR_NO_CONNECTION)) { NoConnectionInterceptor(androidContext()) }
    factory<Interceptor>(named(INTERCEPTOR_HTTP_REQUESTS)) {
        HttpLoggingInterceptor().apply {
            if (BuildConfig.DEBUG) {
                level = HttpLoggingInterceptor.Level.BODY
            }
        }
    }

    single(named(OKHTTP_FOR_HOME)) {
        OkHttpClient.Builder()
            .addInterceptor(get<Interceptor>(named(INTERCEPTOR_NO_CONNECTION)))
            .addInterceptor(get<Interceptor>(named(INTERCEPTOR_HTTP_REQUESTS)))
            .connectTimeout(get<TimeoutSettings>().getConnectionTimeout(), TimeUnit.SECONDS)
            .readTimeout(get<TimeoutSettings>().getReadTimeout(), TimeUnit.SECONDS)
            .writeTimeout(get<TimeoutSettings>().getWriteTimeout(), TimeUnit.SECONDS)
            .build()
    }

    single(named(RETROFIT_HOME)) {
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(get()))
            .client(get(named(OKHTTP_FOR_HOME)))
            .build()
    }

    single {
        get<Retrofit>(named(RETROFIT_HOME)).create(HomeService::class.java)
    }
}