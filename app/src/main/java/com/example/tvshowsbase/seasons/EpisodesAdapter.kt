package com.example.tvshowsbase.seasons

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.model.Episode
import com.example.tvshowsbase.R
import com.example.tvshowsbase.databinding.EpisodeItemBinding

class EpisodesAdapter : ListAdapter<Episode, EpisodesAdapter.EpisodeViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpisodeViewHolder =
        EpisodeViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.episode_item,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: EpisodeViewHolder, position: Int) {
        holder.episodeItemBinding.episode = getItem(position)
    }

    inner class EpisodeViewHolder(val episodeItemBinding: EpisodeItemBinding) :
        RecyclerView.ViewHolder(episodeItemBinding.root)

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Episode>() {
            override fun areItemsTheSame(oldItem: Episode, newItem: Episode) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Episode, newItem: Episode) =
                oldItem == newItem
        }
    }
}
