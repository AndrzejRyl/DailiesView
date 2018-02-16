package com.phenomaly.hifamily.libraries.hifamilydailiesview

import android.support.v4.content.ContextCompat
import android.view.View

fun View.getColor(id: Int): Int = ContextCompat.getColor(context, id)