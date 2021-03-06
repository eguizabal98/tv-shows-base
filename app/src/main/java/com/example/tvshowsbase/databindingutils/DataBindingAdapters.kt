package com.example.tvshowsbase.databindingutils

import android.view.View
import androidx.databinding.BindingAdapter

@BindingAdapter("app:visibleOrGone")
fun View.visible(state: Boolean) {
    visibility = if (state) {
        View.VISIBLE
    } else {
        View.GONE
    }
}