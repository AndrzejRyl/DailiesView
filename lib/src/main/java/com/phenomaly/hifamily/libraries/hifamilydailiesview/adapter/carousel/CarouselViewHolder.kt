package com.phenomaly.hifamily.libraries.hifamilydailiesview.adapter.carousel

import android.view.View
import android.widget.ImageView
import com.phenomaly.hifamily.libraries.hifamilydailiesview.R

class CarouselViewHolder(val itemView: View) {

    private val iconImageView: ImageView = itemView.findViewById(R.id.carousel_item_image_view)

    val left: Int by lazy { itemView.left }

    val x: Float by lazy { itemView.x }

    val width: Int by lazy { itemView.width }

    fun setAlpha(alpha: Float) {
        itemView.alpha = alpha
    }

    fun setTranslationY(translationY: Float) {
        itemView.translationY = translationY
    }

    fun setItemImage(drawableId: Int) {
        iconImageView.setImageResource(drawableId)
    }
}