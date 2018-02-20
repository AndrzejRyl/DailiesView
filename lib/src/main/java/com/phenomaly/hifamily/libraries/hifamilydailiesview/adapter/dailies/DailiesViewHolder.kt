package com.phenomaly.hifamily.libraries.hifamilydailiesview.adapter.dailies

import android.support.v7.widget.RecyclerView
import android.util.TypedValue
import android.view.View
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.phenomaly.hifamily.libraries.hifamilydailiesview.R
import com.phenomaly.hifamily.libraries.hifamilydailiesview.hide
import com.phenomaly.hifamily.libraries.hifamilydailiesview.show

class DailiesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    @BindView(R.id.header)
    lateinit var headerTextView: TextView

    @BindView(R.id.daily)
    lateinit var dailyTextView: TextView

    init {
        ButterKnife.bind(this, itemView)
    }

    fun setHeader(header: String) {
        headerTextView.text = header
    }

    fun setDailyMessage(message: String?) {
        message?.let {
            dailyTextView.text = it
        }
    }

    fun setupStyle(
            shouldShowHeader: Boolean,
            headerColor: Int,
            headerTextSize: Float,
            dailiesColor: Int,
            dailiesTextSize: Float) {

        when (shouldShowHeader) {
            true -> {
                headerTextView.show()
                headerTextView.setTextColor(headerColor)
                headerTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, headerTextSize)
            }
            else -> headerTextView.hide()
        }

        dailyTextView.setTextColor(dailiesColor)
        dailyTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, dailiesTextSize)
    }
}