package com.phenomaly.hifamily.libraries.hifamilydailiesview.adapter.carousel

import android.view.View
import android.widget.ImageView
import butterknife.BindView
import butterknife.ButterKnife
import com.phenomaly.hifamily.libraries.hifamilydailiesview.R2

class CarouselViewHolder(val itemView: View) {

    @BindView(R2.id.carousel_item_image_view)
    lateinit var iconImageView: ImageView

    val left: Int by lazy { itemView.left }

    val x: Float by lazy { itemView.x }

    val width: Int by lazy { itemView.width }

    init {
        ButterKnife.bind(this, itemView)
    }

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