package com.code.challenge.ui.loading

import android.app.Dialog
import android.content.Context
import android.view.Window
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.code.challenge.ui.R.layout
import com.code.challenge.ui.R.style
import timber.log.Timber
import java.lang.ref.WeakReference
import java.util.concurrent.atomic.AtomicBoolean

class LoadingHandler(private val context: Context) : DefaultLifecycleObserver {

    private var isLoading = AtomicBoolean(false)
    private var dialogRef: WeakReference<Dialog>? = null

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)

        val dialog = Dialog(context, style.Photo_Arena_Dialog_Loading)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(layout.layout_loading)
        dialog.setCancelable(false)
        this.dialogRef = WeakReference<Dialog>(dialog)
    }

    override fun onDestroy(owner: LifecycleOwner) {
        super.onDestroy(owner)
        if (isLoading.get()) {
            hide()
        }
        dialogRef?.clear()
    }

    fun hide() {
        Timber.tag(TAG).d("Loading(${hashCode()}) status before hide: ${isLoading.get()}")
        if (isLoading.compareAndSet(true, false)) {
            Timber.tag(TAG).d("Hiding loading dialog(${hashCode()})")
            dialogRef?.get()?.dismiss()
        } else {
            Timber.tag(TAG).d("Hiding missed for (${hashCode()})")
        }
    }

    fun show() {
        Timber.tag(TAG).d("Loading(${hashCode()}) status before show: ${isLoading.get()}")
        if (isLoading.compareAndSet(false, true)) {
            Timber.tag(TAG).d("Showing loading dialog(${hashCode()})")
            dialogRef?.get()?.show()
        } else {
            Timber.tag(TAG).d("Showing missed for (${hashCode()})")
        }
    }

    fun set(shouldShown: Boolean) {
        if (shouldShown) {
            show()
        } else {
            hide()
        }
    }

    private companion object {
        private const val TAG = "Loading"
    }
}
