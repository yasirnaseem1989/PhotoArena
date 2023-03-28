package com.code.challenge.ui.widget

import android.content.Context
import android.util.AttributeSet
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.code.challenge.ui.R

class SwipeRefreshLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : SwipeRefreshLayout(context, attrs) {

    init {
        setColorSchemeResources(R.color.colorBrand)
    }

    fun onRefreshed(autoDismiss: Boolean = true, block: () -> Unit) {

        setOnRefreshListener {
            block()

            if (autoDismiss) {
                postDelayed({ isRefreshing = false }, DURATION_SWIPE_ANIMATION)
            }
        }
    }

    private companion object {
        private const val DURATION_SWIPE_ANIMATION = 500L
    }
}