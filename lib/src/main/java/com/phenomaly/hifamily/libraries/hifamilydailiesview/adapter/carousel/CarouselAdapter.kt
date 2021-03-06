package com.phenomaly.hifamily.libraries.hifamilydailiesview.adapter.carousel

import android.content.Context
import android.view.View.inflate
import android.widget.LinearLayout
import com.phenomaly.hifamily.libraries.hifamilydailiesview.R
import com.phenomaly.hifamily.libraries.hifamilydailiesview.view.CarouselScrollView

class CarouselAdapter(
        private val context: Context,
        private val pointsCount: Int,
        private val carouselDrawableId: Int,
        private val availableDailies: Int) {

    companion object {
        private const val DEFAULT_SCROLL_TRESHOLD = 20
        private const val UNITIALIZED_VIEW_HOLDER_WIDTH = -1
        private const val CENTER_TRANSLATION_CONST = 1f
        private const val BORDER_TRANSLATION_CONST = .3f

        private const val CAROUSEL_CENTER_ITEM_ALPHA = 1f
        private const val CAROUSEL_AVAILABLE_ITEM_ALPHA = .5f
        private const val CAROUSEL_DISABLED_ITEM_ALPHA = .15f
    }

    lateinit var scrollView: CarouselScrollView
    lateinit var points: List<CarouselViewHolder>

    private var viewHolderWidth: Int = UNITIALIZED_VIEW_HOLDER_WIDTH

    fun onViewReady() {
        val layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT)

        points = (0 until pointsCount)
                .map { inflate(context, R.layout.view_carousel_item, null) }
                .map {
                    it.layoutParams = layoutParams
                    CarouselViewHolder(it)
                }
                .onEach { it.setItemImage(carouselDrawableId) }
                .toList()

        points.forEachIndexed { index, viewHolder ->
            setupViewHolderAlpha(index)
            scrollView.contentLayout.addView(viewHolder.itemView)
        }
    }

    fun onScrollChanged(screenCenter: Int) {
        // Stop at the end
        if (screenCenter > points[availableDailies - 1].x) {
            scrollView.scrollTo(points[availableDailies - 1].x.toInt(), scrollView.y.toInt())
            return
        }

        val centerIndex = getCenterIndex(screenCenter)
        scrollView.onScrolled(centerIndex)

        initializeViewHolderWidth()
        setupCenterViews(centerIndex, screenCenter)
        resetSurroundingViews(centerIndex)
    }

    fun getChildAt(position: Int): CarouselViewHolder = points[position]

    private fun setupCenterViews(centerIndex: Int, screenCenter: Int) {
        val leftView = if (centerIndex > 0) points[centerIndex - 1] else null
        val centerView = points[centerIndex]
        val rightView = if (centerIndex < points.size - 2) points[centerIndex + 1] else null

        leftView?.let {
            it.setTranslationY(getBorderTranslation(it.left, screenCenter))
        }

        centerView.setTranslationY(getCenterTranslation(centerView.left, screenCenter))
        centerView.setAlpha(CAROUSEL_CENTER_ITEM_ALPHA)

        rightView?.let {
            it.setTranslationY(getBorderTranslation(it.left, screenCenter))
        }
    }

    private fun resetSurroundingViews(centerIndex: Int) {
        (centerIndex - DEFAULT_SCROLL_TRESHOLD..centerIndex + DEFAULT_SCROLL_TRESHOLD)
                .filter { it in (0..points.size) }
                .forEach {
                    if (Math.abs(it - centerIndex) > 1) {
                        points[it].setTranslationY(0f)
                    }
                    if (it != centerIndex) {
                        setupViewHolderAlpha(it)
                    }
                }
    }

    private fun getCenterIndex(center: Int): Int {
        val viewInCenter = points
                .minWith(Comparator { o1, o2 -> Math.abs(o1.left - center).compareTo(Math.abs(o2.left - center)) })

        return points.indexOf(viewInCenter)
    }

    private fun getCenterTranslation(leftBorder: Int, screenCenter: Int): Float {
        val maximumDistanceToCenter = viewHolderWidth / 2f
        val currentDistanceToCenter = Math.abs(leftBorder - screenCenter)
        val translationFactor = CENTER_TRANSLATION_CONST +
                (maximumDistanceToCenter - currentDistanceToCenter) / maximumDistanceToCenter

        return getTranslation(translationFactor)
    }

    private fun getBorderTranslation(leftBorder: Int, screenCenter: Int): Float {
        val maximumDistanceToCenter = viewHolderWidth / 2f * 3
        val currentDistanceToCenter = Math.abs(leftBorder - screenCenter)
        val translationFactor = BORDER_TRANSLATION_CONST +
                (maximumDistanceToCenter - currentDistanceToCenter) / maximumDistanceToCenter

        return getTranslation(translationFactor)
    }

    private fun getTranslation(translationFactor: Float): Float {
        val maxTranslation = -1 * viewHolderWidth / 2f
        return Math.min(0f, maxTranslation * translationFactor)
    }

    private fun initializeViewHolderWidth() {
        if (pointsCount > 0 && viewHolderWidth == UNITIALIZED_VIEW_HOLDER_WIDTH) {
            viewHolderWidth = points[0].width
        }
    }

    private fun setupViewHolderAlpha(index: Int) {
        when (index >= availableDailies) {
            true -> points[index].setAlpha(CAROUSEL_DISABLED_ITEM_ALPHA)
            else -> points[index].setAlpha(CAROUSEL_AVAILABLE_ITEM_ALPHA)
        }
    }
}