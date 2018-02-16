package com.phenomaly.hifamily.libraries.hifamilydailiesview.view

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.widget.LinearLayout
import butterknife.BindDimen
import butterknife.BindView
import butterknife.ButterKnife
import com.phenomaly.hifamily.libraries.hifamilydailiesview.R
import com.phenomaly.hifamily.libraries.hifamilydailiesview.adapter.dailies.DailiesAdapter
import com.phenomaly.hifamily.libraries.hifamilydailiesview.getColor

class HiFamilyDailiesView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    @BindView(R.id.dailies_pager)
    lateinit var dailiesViewPager: DailiesViewPager

    @JvmField
    @BindDimen(R.dimen.header_text_size)
    var headerTextSize: Float = -1f

    @JvmField
    @BindDimen(R.dimen.dailies_text_size)
    var dailiesTextSize: Float = -1f

    private var shouldShowHeader: Boolean = true
    private var headerColor = getColor(R.color.default_text_color)
    private var dailiesColor = getColor(R.color.default_text_color)

    private lateinit var dailiesAdapter: DailiesAdapter
    private lateinit var dailiesPageListener: OnPageSelectedListener

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
            recycle()
        }
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        ButterKnife.bind(this)
    }

    fun init(currentIndex: Int, availableDailies: Map<String, String>) {
        initDailiesAdapter(availableDailies)

        dailiesViewPager.setCurrentItem(currentIndex, true)
    }

    private fun initDailiesAdapter(availableDailies: Map<String, String>) {
        dailiesAdapter = DailiesAdapter(
                availableDailies,
                shouldShowHeader,
                headerColor,
                headerTextSize,
                dailiesColor,
                dailiesTextSize
        )

        dailiesAdapter.context = context
        dailiesViewPager.adapter = dailiesAdapter
        dailiesPageListener = { Log.i(javaClass.name, "Selected position no ${it}!") }
        dailiesViewPager.addCustomOnPageSelectedListener(dailiesPageListener)
    }
}