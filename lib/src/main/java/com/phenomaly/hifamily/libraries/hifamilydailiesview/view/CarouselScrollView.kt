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