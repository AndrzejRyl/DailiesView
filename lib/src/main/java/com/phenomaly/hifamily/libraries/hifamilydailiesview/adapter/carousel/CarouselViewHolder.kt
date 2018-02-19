package com.phenomaly.hifamily.libraries.hifamilydailiesview.adapter.carousel

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import butterknife.BindView
import butterknife.ButterKnife
import com.phenomaly.hifamily.libraries.hifamilydailiesview.R

class CarouselViewHolder(val itemView: View) {

    @BindView(R.id.carousel_item_image_view)
    lateinit var iconImageView: ImageView

    var alpha: Float = 1f
        set(value) {
            itemView.alpha = value
        }

    var translationY: Float = itemView.translationY
        set(value) {
            itemView.translationY = value
        }

    val left: Int by lazy { itemView.left }

    val x: Float by lazy { itemView.x }

    val width: Int by lazy { itemView.width }

    init {
        ButterKnife.bind(this, itemView)
    }

    fun setItemImage(drawable: Drawable) {
        iconImageView.setImageDrawable(drawable)
    }
}