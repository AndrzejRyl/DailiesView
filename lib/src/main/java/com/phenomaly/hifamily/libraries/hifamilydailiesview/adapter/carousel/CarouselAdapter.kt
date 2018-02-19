package com.phenomaly.hifamily.libraries.hifamilydailiesview.adapter.carousel

import android.content.Context
import android.view.View.inflate
import android.widget.LinearLayout
import com.phenomaly.hifamily.libraries.hifamilydailiesview.R
import com.phenomaly.hifamily.libraries.hifamilydailiesview.view.CarouselScrollView

class CarouselAdapter(
        private val pointsCount: Int) {

    companion object {
        private const val DEFAULT_SCROLL_TRESHOLD = 20
    }

    lateinit var context: Context
    lateinit var scrollView: CarouselScrollView
    lateinit var points: List<CarouselViewHolder>

    fun onViewReady() {
        val layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT)

        points = (0..pointsCount)
                .map { inflate(context, R.layout.view_carousel_item, null) }
                .map {
                    it.layoutParams = layoutParams
                    CarouselViewHolder(it)
                }
                .toList()

        points.forEach {
            scrollView.contentLayout.addView(it.itemView)
        }
    }

    fun onScrollChanged(center: Int) {
        // Stop at the end
        if (center > points.last().x) {
            scrollView.scrollTo(points.last().x.toInt(), scrollView.y.toInt())
            return
        }

        val centerIndex = getCenterIndex(center)
        scrollView.onScrolled(centerIndex)

        setupCenterViews(centerIndex, center)
        resetSurroundingViews(centerIndex)
    }

    private fun setupCenterViews(centerIndex: Int, center: Int) {
        val leftView = if (centerIndex > 0) points[centerIndex - 1] else null
        val centerView = points[centerIndex]
        val rightView = if (centerIndex < points.size - 2) points[centerIndex + 1] else null

        leftView?.let {
            it.translationY = scrollView.getBorderTranslation(it.left, center)
        }

        centerView.translationY = scrollView.getCenterTranslation(centerView.left, center)

        rightView?.let {
            it.translationY = scrollView.getBorderTranslation(it.left, center)
        }
    }

    private fun resetSurroundingViews(centerIndex: Int) {
        (centerIndex - DEFAULT_SCROLL_TRESHOLD..centerIndex + DEFAULT_SCROLL_TRESHOLD).forEach {
            if (it >= 0 && it < points.size && Math.abs(it - centerIndex) > 1) {
                points[it]
                        .translationY = 0f
            }
        }
    }

    private fun getCenterIndex(center: Int): Int {
        val viewInCenter = points
                .minWith(Comparator { o1, o2 -> Math.abs(o1.left - center).compareTo(Math.abs(o2.left - center)) })

        return points.indexOf(viewInCenter)
    }

    fun getChildAt(position: Int): CarouselViewHolder = points[position]
}