package com.phenomaly.hifamily.libraries.hifamilydailiesview.view

import android.animation.ObjectAnimator
import android.content.Context
import android.util.AttributeSet
import android.widget.HorizontalScrollView
import android.widget.LinearLayout
import butterknife.BindView
import butterknife.ButterKnife
import com.phenomaly.hifamily.libraries.hifamilydailiesview.R
import com.phenomaly.hifamily.libraries.hifamilydailiesview.adapter.carousel.CarouselAdapter

typealias OnScrolledListener = (Int) -> Unit

class CarouselScrollView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : HorizontalScrollView(context, attrs, defStyleAttr) {

    @BindView(R.id.carousel_scrollview_content)
    lateinit var contentLayout: LinearLayout

    var adapter: CarouselAdapter? = null
        set(value) {
            field = value
            field?.scrollView = this
            field?.onViewReady()
        }

    private val scrollListeners = arrayListOf<OnScrolledListener>()

    override fun onFinishInflate() {
        super.onFinishInflate()
        ButterKnife.bind(this)
    }

    override fun onScrollChanged(scrollX: Int, scrollY: Int, oldScrollX: Int, oldScrollY: Int) {
        adapter?.onScrollChanged(scrollX)
    }

    // TODO Refactor
    // 15 is probably the height of the view (2 x 7dp)
    // 17 is the width of the view (14) + half of the margin (4)
    // 51 is prbably 3 x 17
    fun getCenterTranslation(leftBorder: Int, center: Int) = Math.min(0f, -15f * (17 - Math.abs(leftBorder - center)) / 17 - 15f)

    fun getBorderTranslation(leftBorder: Int, center: Int) = Math.min(0f, -15f * (51 - Math.abs(leftBorder - center)) / 51)

    fun smoothScrollToPosition(position: Int) {
        val point = adapter?.getChildAt(position)
        point?.let {
            val center = it.x.toInt()
            ObjectAnimator.ofInt(this, "scrollX", center).setDuration(200).start()
        }
    }

    fun addCustomScrollListener(carouselOnScrolledListener: OnScrolledListener) {
        scrollListeners.add(carouselOnScrolledListener)
    }

    fun clearOnScrollListeners() {
        scrollListeners.clear()
    }

    fun onScrolled(centerIndex: Int) {
        scrollListeners.forEach { it.invoke(centerIndex) }
    }
}