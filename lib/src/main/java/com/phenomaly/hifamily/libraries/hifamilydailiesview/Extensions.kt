package com.phenomaly.hifamily.libraries.hifamilydailiesview

import android.content.Context
import android.graphics.Point
import android.support.v4.content.ContextCompat
import android.view.View
import android.view.WindowManager

internal fun View.getColor(id: Int): Int = ContextCompat.getColor(context, id)

internal fun View.getDimension(id: Int): Float = context.resources.getDimension(id)

internal fun View.hide() {
    visibility = View.GONE
}

internal fun View.show() {
    visibility = View.VISIBLE
}

internal fun Context.getScreenWidth(): Int {
    val wm = this.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    val display = wm.defaultDisplay
    val size = Point()
    display.getSize(size)
    return size.x
}