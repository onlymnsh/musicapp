package com.example.practicaltestapplication.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "songs")
data class SongData(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "song_image_url") val imageUrl: String = "",
    @ColumnInfo(name = "title") val title: String = ""
)