package com.phenomaly.hifamily.libraries.hifamilydailiesview

import android.support.v4.content.ContextCompat
import android.view.View

internal fun View.getColor(id: Int): Int = ContextCompat.getColor(context, id)

internal fun View.hide() {
    visibility = View.GONE
}

internal fun View.show() {
    visibility = View.VISIBLE
}