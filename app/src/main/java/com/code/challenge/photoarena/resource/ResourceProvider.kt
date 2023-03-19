package com.code.challenge.photoarena.resource

import android.content.Context
import android.content.res.Configuration
import android.os.LocaleList
import com.code.challenge.photoarena.R
import java.util.Locale

abstract class ResourceProvider(context: Context) {

    protected val localizedContext: Context

    init {
        val configuration = Configuration(context.resources.configuration)
        configuration.setLocales(LocaleList.forLanguageTags(Locale.getDefault().language))
        localizedContext = context.createConfigurationContext(configuration)
    }

    open fun getCommonError(): String = localizedContext.getString(R.string.common_error_default)
}
