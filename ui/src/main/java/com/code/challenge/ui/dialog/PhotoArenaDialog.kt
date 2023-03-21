package com.code.challenge.ui.dialog

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.code.challenge.ui.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class PhotoArenaDialog(context: Context) {

    internal val view = LayoutInflater.from(context).inflate(R.layout.dialog_photo_arena, null)

    val dialog =
        MaterialAlertDialogBuilder(context, R.style.ThemeOverlay_App_MaterialAlertDialog).setView(
            view
        ).create()

    fun show() {
        dialog.show()
    }

    fun hide() {
        dialog.dismiss()
    }

    fun isShowing(): Boolean = dialog.isShowing

    fun <V : View> getView(id: Int): V = view.findViewById(id)

}

fun photoArenaDialog(context: Context, init: PhotoArenaDialog.() -> Unit): PhotoArenaDialog {
    val dialog = PhotoArenaDialog(context)
    dialog.onPositive("Default") {
        hide()
    }
    dialog.init()
    return dialog
}

fun PhotoArenaDialog.title(title: String) {
    val titleView = getView<TextView>(R.id.dialogTitleTextView)
    titleView.text = title
}

fun PhotoArenaDialog.onPositive(title: String, onClick: PhotoArenaDialog.() -> Unit) {
    val positiveButton = getView<Button>(R.id.dialogPositiveButton)
    positiveButton.text = title
    positiveButton.setOnClickListener {
        hide()
        onClick()
    }
}

fun PhotoArenaDialog.onNegative(title: String, onClick: PhotoArenaDialog.() -> Unit) {
    val negativeButton = getView<Button>(R.id.dialogNegativeButton)
    negativeButton.text = title
    negativeButton.setOnClickListener {
        hide()
        onClick()
    }
}