package com.code.challenge.photoarena.resource

import android.content.Context
import com.code.challenge.photoarena.R

open class PhotoArenaResourceProvider(context: Context) : ResourceProvider(context) {

    override fun getCommonError(): String =
        localizedContext.getString(R.string.common_error_default)
}