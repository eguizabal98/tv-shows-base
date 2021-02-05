package com.example.tvshowsbase.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.tvshowsbase.R

@BindingAdapter("app:imageUrl")
fun ImageView.setImageUrl(Url: String?) {
    val placeholder = CircularProgressDrawable(this.context).apply {
        strokeWidth = 5f
        centerRadius = 30f
        start()
    }
    Url?.let { it ->
        Glide.with(this.context)
            .load(it)
            .fitCenter()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .placeholder(placeholder)
            .error(
                Glide.with(this.context).load(it).placeholder(placeholder)
                    .error(R.drawable.ic_baseline_account_circle_24)
            )
            .into(this)
    }

}
