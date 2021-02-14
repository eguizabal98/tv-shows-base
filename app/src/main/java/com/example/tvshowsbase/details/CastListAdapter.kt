package com.example.tvshowsbase.details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.model.Cast
import com.example.tvshowsbase.R
import com.example.tvshowsbase.databinding.CastListItemBinding

class CastListAdapter : ListAdapter<Cast, CastListAdapter.CastViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CastViewHolder =
        CastViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.cast_list_item,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: CastViewHolder, position: Int) {
        holder.castListItemBinding.cast = getItem(position)
    }

    inner class CastViewHolder(val castListItemBinding: CastListItemBinding) :
        RecyclerView.ViewHolder(castListItemBinding.root)

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Cast>() {
            override fun areItemsTheSame(oldItem: Cast, newItem: Cast) =
                oldItem.name == newItem.name

            override fun areContentsTheSame(oldItem: Cast, newItem: Cast) =
                oldItem == newItem
        }
    }
}

