package com.code.challenge.ui.widget

import android.content.Context
import android.graphics.drawable.BitmapDrawable
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import coil.load
import com.code.challenge.ui.R
import com.code.challenge.ui.databinding.PhotoarenaCardviewLayoutBinding
import com.code.challenge.ui.model.PhoneArenaCard


class PhotoArenaCardView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val binding: PhotoarenaCardviewLayoutBinding =
        PhotoarenaCardviewLayoutBinding.inflate(LayoutInflater.from(context), this)

    fun setCard(item: PhoneArenaCard) {
        with(binding) {

            phoneArenaCardImageView.apply {
                load(item.imageUrl) {
                    target(onSuccess = { result ->
                        val bitmap = (result as BitmapDrawable).bitmap
                        setImageBitmap(bitmap)
                    }, onError = { setImageResource(R.drawable.background_cofe_card_view) })
                }
            }

            titleTextView.text = item.title
            badgeTextView.text = item.tags
            subTitleTextView.text = item.subtitle
            bodyTextView.text = item.subtitleBody
            descriptionTextView.text = item.description
        }
    }
}