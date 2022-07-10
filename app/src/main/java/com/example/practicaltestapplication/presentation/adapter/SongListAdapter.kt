package com.example.practicaltestapplication.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.practicaltestapplication.databinding.ItemSongsBinding
import com.example.practicaltestapplication.domain.model.SongData
import com.example.practicaltestapplication.utils.loadPreviewImage

class SongListAdapter(private val listener: ItemClickListener) :
    ListAdapter<SongData, SongListAdapter.SongViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
        val binding = ItemSongsBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return SongViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        holder.itemView.setOnClickListener { listener.onItemClick(getItem(position)) }
        holder.bind(getItem(position))
    }

    class SongViewHolder(private val itemBinding: ItemSongsBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(song: SongData) {
            itemBinding.apply {
                songTitle.text = song.title
                loadPreviewImage(songPreview, song.imageUrl)
            }
        }
    }

    private object DiffCallback : DiffUtil.ItemCallback<SongData>() {
        override fun areItemsTheSame(oldItem: SongData, newItem: SongData): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: SongData, newItem: SongData): Boolean {
            return oldItem == newItem
        }

    }
}