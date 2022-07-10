package com.example.practicaltestapplication.data.model

import com.google.gson.annotations.SerializedName

data class SongDto(
    @SerializedName("feed")
    val feed: SongFeedDto,
)

