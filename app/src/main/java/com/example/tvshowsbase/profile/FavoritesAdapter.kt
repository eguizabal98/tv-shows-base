package com.example.tvshowsbase.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.model.TvShow
import com.example.tvshowsbase.R
import com.example.tvshowsbase.databinding.FavoriteItemBinding

class FavoritesAdapter :
    ListAdapter<TvShow, FavoritesAdapter.ShowsViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShowsViewHolder =
        ShowsViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.favorite_item,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ShowsViewHolder, position: Int) {
        val item: TvShow? = getItem(position)
        holder.favoriteItemBinding.tvShow = item
        holder.favoriteItemBinding.executePendingBindings()
    }

    inner class ShowsViewHolder(val favoriteItemBinding: FavoriteItemBinding) :
        RecyclerView.ViewHolder(favoriteItemBinding.root)

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<TvShow>() {
            override fun areItemsTheSame(oldItem: TvShow, newItem: TvShow) =
                oldItem.showId == newItem.showId

            override fun areContentsTheSame(oldItem: TvShow, newItem: TvShow) =
                oldItem == newItem
        }
    }
}
