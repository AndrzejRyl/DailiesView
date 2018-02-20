package com.phenomaly.hifamily.libraries.hifamilydailiesview.view

import android.content.Context
import android.support.v4.view.ViewPager
import android.util.AttributeSet

typealias OnPageSelectedListener = (Int) -> Unit

class DailiesViewPager @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null
) : ViewPager(context, attrs) {

    private val onPageListeners = arrayListOf<OnPageSelectedListener>()

    init {
        addOnPageChangeListener(
                object : ViewPager.OnPageChangeListener {
                    override fun onPageScrollStateChanged(state: Int) {
                    }

                    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                    }

                    override fun onPageSelected(position: Int) {
                        onPageListeners.forEach { it(position) }
                    }
                }
        )
    }

    fun addCustomOnPageSelectedListener(listener: OnPageSelectedListener) {
        onPageListeners.add(listener)
    }

    fun clearCustomOnPageSelectedListeners() {
        onPageListeners.clear()
    }
}