package com.example.tvshowsbase.databindingutils

import android.view.View
import androidx.databinding.BindingAdapter

@BindingAdapter("app:visibleOrGone")
fun visibleOrGone(view: View, state: Boolean) {
    if (state) {
        view.visibility = View.VISIBLE
    } else {
        view.visibility = View.GONE
    }
}