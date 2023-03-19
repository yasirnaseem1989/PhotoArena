package com.code.challenge.ui.loading

import com.code.challenge.ui.loading.loading.LoadingHandler
import org.koin.dsl.module

val uiModule = module {

    factory { params ->
        LoadingHandler(context = params.get())
    }
}
