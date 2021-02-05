package com.example.tvshowsbase.showslist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.model.TvShow
import com.example.tvshowsbase.R
import com.example.tvshowsbase.databinding.TvShowsItemBinding

class TvShowsAdapter(private val itemClickListener: ItemClickListener) :
    PagedListAdapter<TvShow, TvShowsAdapter.ShowsViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShowsViewHolder =
        ShowsViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.tv_shows_item,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ShowsViewHolder, position: Int) {
        val item: TvShow? = getItem(position)
        holder.tvShowsItemBinding.tvShow = item
        holder.tvShowsItemBinding.clickListener = itemClickListener
        holder.tvShowsItemBinding.executePendingBindings()
    }

    inner class ShowsViewHolder(val tvShowsItemBinding: TvShowsItemBinding) :
        RecyclerView.ViewHolder(tvShowsItemBinding.root)

    interface ItemClickListener {
        fun onItemClicked(tvShow: TvShow)
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<TvShow>() {
            override fun areItemsTheSame(oldItem: TvShow, newItem: TvShow) =
                oldItem.showId == newItem.showId

            override fun areContentsTheSame(oldItem: TvShow, newItem: TvShow) =
                oldItem == newItem
        }
    }
}