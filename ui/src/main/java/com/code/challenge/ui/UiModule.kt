package com.code.challenge.ui

import com.code.challenge.ui.loading.LoadingHandler
import org.koin.dsl.module

val uiModule = module {

    factory { params ->
        LoadingHandler(context = params.get())
    }
}
