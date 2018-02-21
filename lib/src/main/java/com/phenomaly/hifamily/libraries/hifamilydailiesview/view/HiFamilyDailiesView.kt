package com.phenomaly.hifamily.libraries.hifamilydailiesview.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import com.phenomaly.hifamily.libraries.hifamilydailiesview.R
import com.phenomaly.hifamily.libraries.hifamilydailiesview.adapter.carousel.CarouselAdapter
import com.phenomaly.hifamily.libraries.hifamilydailiesview.adapter.dailies.DailiesAdapter
import com.phenomaly.hifamily.libraries.hifamilydailiesview.getColor
import com.phenomaly.hifamily.libraries.hifamilydailiesview.getDimension
import com.phenomaly.hifamily.libraries.hifamilydailiesview.getScreenWidth

enum class DailyElement {
    CAROUSEL, DAILIES
}

typealias OnDailyElementTouchedListener = (element: DailyElement) -> Unit
typealias DailySelectedListener = (index: Int) -> Unit

class HiFamilyDailiesView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private lateinit var carouselScrollView: CarouselScrollView
    private lateinit var dailiesViewPager: DailiesViewPager
    private lateinit var additionalViewContainer: LinearLayout

    private var shouldShowHeader: Boolean = true
    private var headerTextSize = getDimension(R.dimen.hiFamilyDailiesView_dailies_header_text_size)
    private var dailiesTextSize = getDimension(R.dimen.hiFamilyDailiesView_dailies_text_size)
    private var headerColor = getColor(R.color.hiFamilyDailiesView_default_text_color)
    private var dailiesColor = getColor(R.color.hiFamilyDailiesView_default_text_color)

    private var carouselDrawableId: Int = R.drawable.default_carousel_icon
    private lateinit var dailiesAdapter: DailiesAdapter
    private lateinit var dailiesPageListener: OnPageSelectedListener

    private lateinit var carouselAdapter: CarouselAdapter
    private lateinit var carouselOnScrolledListener: OnScrolledListener

    private val dailySelectedListeners = arrayListOf<DailySelectedListener>()
    private val onTouchListeners = arrayListOf<OnDailyElementTouchedListener>()

    private var currentDailyElement = DailyElement.CAROUSEL

    init {
        inflate(context, R.layout.view_hi_family_dailies, this)
        attrs?.let { initTypedArray(it) }
    }

    private fun initTypedArray(attrs: AttributeSet) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.HiFamilyDailiesView)

        typedArray.apply {
            shouldShowHeader = getBoolean(R.styleable.HiFamilyDailiesView_header, shouldShowHeader)
            headerColor = getColor(R.styleable.HiFamilyDailiesView_header_color, headerColor)
            dailiesColor = getColor(R.styleable.HiFamilyDailiesView_dailies_color, dailiesColor)
            headerTextSize = getDimension(R.styleable.HiFamilyDailiesView_header_text_size, headerTextSize)
            dailiesTextSize = getDimension(R.styleable.HiFamilyDailiesView_dailies_text_size, dailiesTextSize)
            carouselDrawableId = getResourceId(R.styleable.HiFamilyDailiesView_carousel_drawable, carouselDrawableId)
            recycle()
        }
    }

    override fun onFinishInflate() {
        super.onFinishInflate()

        carouselScrollView = findViewById(R.id.carousel_scrollview)
        dailiesViewPager = findViewById(R.id.dailies_pager)
        additionalViewContainer = findViewById(R.id.hifamily_dailies_additional_view_container)

        dailiesViewPager.setOnTouchListener { _, _ -> onDailiesTouched() }
        carouselScrollView.setOnTouchListener { _, _ -> onCarouselTouched() }
    }

    fun init(currentIndex: Int, allDailiesCount: Int, availableDailies: Map<String, String>) {
        initDailiesAdapter(availableDailies)
        initCarouselAdapter(allDailiesCount, availableDailies.size)
        initCarouselRecyclerPadding()
        initCarouselScrollViewPosition(currentIndex)
    }

    fun addAdditionalView(view: View) {
        additionalViewContainer.addView(view)
    }

    fun clearAdditionalViews() {
        additionalViewContainer.removeAllViews()
    }

    fun addOnDailySelectedListener(listener: DailySelectedListener) {
        dailySelectedListeners.add(listener)
    }

    fun clearDailySelectedListeners() {
        dailySelectedListeners.clear()
    }

    fun getCurrentDailyIndex() = dailiesViewPager.currentItem

    fun addOnTouchListener(listener: OnDailyElementTouchedListener) {
        onTouchListeners.add(listener)
    }

    fun clearOnTouchListeners() {
        onTouchListeners.clear()
    }

    private fun initDailiesAdapter(availableDailies: Map<String, String>) {
        dailiesAdapter = DailiesAdapter(
                context,
                availableDailies,
                shouldShowHeader,
                headerColor,
                headerTextSize,
                dailiesColor,
                dailiesTextSize
        )

        dailiesViewPager.adapter = dailiesAdapter
        dailiesPageListener = { position ->
            dailySelectedListeners.forEach { it.invoke(position) }
            carouselScrollView
                    .smoothScrollToPosition(position)
        }
    }

    private fun initCarouselRecyclerPadding() {
        val width = context.getScreenWidth()
        carouselScrollView.setPaddingRelative(width / 2, 0, width / 2, 0)
    }

    private fun initCarouselAdapter(allDailiesCount: Int, availableDailiesCount: Int) {
        carouselAdapter = CarouselAdapter(
                context,
                allDailiesCount,
                carouselDrawableId,
                availableDailiesCount
        )

        carouselScrollView.adapter = carouselAdapter
        carouselOnScrolledListener = { dailiesViewPager.setCurrentItem(it, false) }
        carouselScrollView.addCustomScrollListener(carouselOnScrolledListener)
    }

    private fun initCarouselScrollViewPosition(currentIndex: Int) {
        carouselScrollView.post {
            when (currentIndex == 0) {
                true -> carouselAdapter.onScrollChanged(carouselAdapter.getChildAt(0).left)
                else -> carouselScrollView.smoothScrollToPosition(currentIndex)
            }
        }
    }

    private fun onDailiesTouched(): Boolean {
        if (currentDailyElement == DailyElement.CAROUSEL) {
            currentDailyElement = DailyElement.DAILIES
            onTouchListeners.forEach { it.invoke(DailyElement.DAILIES) }

            carouselScrollView.clearOnScrollListeners()
            dailiesViewPager.addCustomOnPageSelectedListener(dailiesPageListener)
        }
        return false
    }

    private fun onCarouselTouched(): Boolean {
        if (currentDailyElement == DailyElement.DAILIES) {
            currentDailyElement = DailyElement.CAROUSEL
            onTouchListeners.forEach { it.invoke(DailyElement.CAROUSEL) }

            dailiesViewPager.clearCustomOnPageSelectedListeners()
            carouselScrollView.addCustomScrollListener(carouselOnScrolledListener)
        }
        return false
    }
}