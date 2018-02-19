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

    init {
        ButterKnife.bind(this, itemView)
    }

    fun setItemImage(drawable: Drawable) {
        iconImageView.setImageDrawable(drawable)
    }

    var translationY: Float
        set(value) {
            itemView.translationY = value
        }
        get() = itemView.translationY

    var left: Int
        set(value) {
            itemView.left = value
        }
        get() = itemView.left

    var x: Float
        set(value) {
            itemView.x = value
        }
        get() = itemView.x

    var width: Int
        set(value) {}
        get() = itemView.width
}