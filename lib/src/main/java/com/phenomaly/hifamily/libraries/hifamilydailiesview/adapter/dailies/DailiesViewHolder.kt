package com.phenomaly.hifamily.libraries.hifamilydailiesview.adapter.dailies

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.phenomaly.hifamily.libraries.hifamilydailiesview.R

class DailiesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    @BindView(R.id.header)
    lateinit var headerTextView: TextView

    @BindView(R.id.daily)
    lateinit var dailyTextView: TextView

    var header: String = ""
        set(value) {
            headerTextView.text = value
        }

    var daily: String? = ""
        set(value) {
            dailyTextView.text = value
        }

    init {
        ButterKnife.bind(this, itemView)
    }

    fun setupStyle(
            shouldShowHeader: Boolean,
            headerColor: Int,
            headerTextSize: Float,
            dailiesColor: Int,
            dailiesTextSize: Float) {

        //todo
    }
}