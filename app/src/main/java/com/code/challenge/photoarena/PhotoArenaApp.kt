package com.code.challenge.photoarena

import android.app.Application
import com.code.challenge.photoarena.di.coroutineDispatcherModule
import com.code.challenge.photoarena.di.networkModule
import com.code.challenge.photoarena.view.fragments.homeModule
import com.code.challenge.ui.loading.uiModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.logger.AndroidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.EmptyLogger
import timber.log.Timber

class PhotoArenaApp : Application() {

    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())

        startKoin {
            if (BuildConfig.DEBUG) {
                AndroidLogger()
            } else {
                EmptyLogger()
            }
            androidContext(this@PhotoArenaApp)
            modules(
                listOf(
                    networkModule,
                    homeModule,
                    coroutineDispatcherModule,
                    uiModule
                )
            )
        }
    }
}