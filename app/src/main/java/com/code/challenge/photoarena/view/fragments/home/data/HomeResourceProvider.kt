package com.code.challenge.photoarena.view.fragments.home.data

import android.content.Context
import com.code.challenge.photoarena.R
import com.code.challenge.photoarena.resource.PhotoArenaResourceProvider

class HomeResourceProvider(context: Context) : PhotoArenaResourceProvider(context) {

    fun getDialogTitle(): String =
        localizedContext.getString(com.code.challenge.ui.R.string.dialog_title_text)

    fun getPositiveButtonText(): String =
        localizedContext.getString(com.code.challenge.ui.R.string.button_positive_text)

    fun getNegativeButtonText(): String =
        localizedContext.getString(com.code.challenge.ui.R.string.button_cancel_text)

    fun getTotalLikesText(totalLike: Int): String =
        localizedContext.getString(R.string.total_likes_text, totalLike.toString())

    fun getTotalDownloadText(totalDownloads: Int): String =
        localizedContext.getString(R.string.total_downloads_text, totalDownloads.toString())

    fun getTotalCommentsText(totalComments: Int): String =
        localizedContext.getString(R.string.total_comments_text, totalComments.toString())
}