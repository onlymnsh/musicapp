package com.example.practicaltestapplication.presentation.adapter

import com.example.practicaltestapplication.domain.model.SongData

interface ItemClickListener {
    fun onItemClick(songData: SongData)
}