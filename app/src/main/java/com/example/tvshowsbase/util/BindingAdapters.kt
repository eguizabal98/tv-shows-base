package com.example.tvshowsbase.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.tvshowsbase.R

@BindingAdapter("app:imageUrlProfile")
fun ImageView.setImageUrlProfile(Url: String?) {
    val placeholder = CircularProgressDrawable(this.context).apply {
        strokeWidth = 5f
        centerRadius = 30f
        start()
    }
    GlideApp.with(this.context)
        .load(Url)
        .centerCrop()
        .circleCrop()
        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
        .placeholder(placeholder)
        .error(
            Glide.with(this.context).load(Url).placeholder(placeholder)
                .error(R.drawable.ic_baseline_account_circle_24)
        )
        .into(this)
}

@BindingAdapter("app:imageUrlShow")
fun ImageView.setImageUrlShow(Url: String?) {
    val placeholder = CircularProgressDrawable(this.context).apply {
        strokeWidth = 5f
        centerRadius = 30f
        start()
    }
    GlideApp.with(this.context)
        .load(Url)
        .centerCrop()
        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
        .placeholder(placeholder)
        .error(R.drawable.showplaceholder)
        .into(this)
}

@BindingAdapter("app:imageUrlBackDrop")
fun ImageView.imageUrlBackDrop(Url: String?) {
    val placeholder = CircularProgressDrawable(this.context).apply {
        strokeWidth = 5f
        centerRadius = 30f
        start()
    }
    GlideApp.with(this.context)
        .load(Url)
        .centerCrop()
        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
        .placeholder(placeholder)
        .into(this)
}

@BindingAdapter("app:imageUrlCast")
fun ImageView.imageUrlCast(Url: String?) {
    val placeholder = CircularProgressDrawable(this.context).apply {
        strokeWidth = 5f
        centerRadius = 30f
        start()
    }
    GlideApp.with(this.context)
        .load(Url)
        .centerCrop()
        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
        .placeholder(placeholder)
        .error(R.drawable.castplaceholder)
        .into(this)
}

@BindingAdapter("app:imageUrlEpisode")
fun ImageView.imageUrlEpisode(Url: String?) {
    val placeholder = CircularProgressDrawable(this.context).apply {
        strokeWidth = 5f
        centerRadius = 30f
        start()
    }
    GlideApp.with(this.context)
        .load(Url)
        .centerCrop()
        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
        .placeholder(placeholder)
        .error(R.drawable.episodeplaceholder)
        .into(this)
}
