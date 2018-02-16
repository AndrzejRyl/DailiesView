package com.phenomaly.hifamily.libraries.hifamilydailiesview.adapter.dailies

import android.content.Context
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.phenomaly.hifamily.libraries.hifamilydailiesview.R

class DailiesAdapter(
        private val dailies: Map<String, String>,
        private val shouldShowHeader: Boolean,
        private val headerColor: Int,
        private val headerTextSize: Float,
        private val dailiesColor: Int,
        private val dailiesTextSize: Float
) : PagerAdapter() {

    lateinit var context: Context

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun getCount(): Int = dailies.size

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val inflater = LayoutInflater.from(context)
        val layout = inflater.inflate(R.layout.view_dailies_item, container, false)

        val holder = DailiesViewHolder(layout)
        val header = dailies.keys.toTypedArray()[position]
        val daily = dailies[header]

        holder.header = header
        holder.daily = daily

        holder.setupStyle(
                shouldShowHeader,
                headerColor,
                headerTextSize,
                dailiesColor,
                dailiesTextSize
        )

        container.addView(layout)
        return layout
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        (container as ViewPager).removeView(`object` as View)
    }
}