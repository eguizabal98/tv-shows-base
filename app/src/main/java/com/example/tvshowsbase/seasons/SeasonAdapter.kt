package com.example.tvshowsbase.seasons

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.model.Season
import com.example.tvshowsbase.R
import com.example.tvshowsbase.databinding.SeasonItemBinding

class SeasonAdapter :
    ListAdapter<Season, SeasonAdapter.SeasonViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeasonViewHolder =
        SeasonViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.season_item,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: SeasonViewHolder, position: Int) {
        val adapter = EpisodesAdapter()
        holder.seasonItemBinding.episodesList.adapter = adapter
        adapter.submitList(getItem(position).episodes)
        holder.seasonItemBinding.seasonName.text = getItem(position).name
    }

    inner class SeasonViewHolder(val seasonItemBinding: SeasonItemBinding) :
        RecyclerView.ViewHolder(seasonItemBinding.root)

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Season>() {
            override fun areItemsTheSame(oldItem: Season, newItem: Season) =
                oldItem.name == newItem.name

            override fun areContentsTheSame(oldItem: Season, newItem: Season) =
                oldItem == newItem
        }
    }
}
