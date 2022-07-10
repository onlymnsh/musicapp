package com.example.practicaltestapplication.data.mappers

import com.example.practicaltestapplication.data.model.SongDto
import com.example.practicaltestapplication.domain.model.SongData

fun SongDto.toSongDataMap(): List<SongData> {
    return feed.songs.map {
        SongData(
            id = it.songIdDto.attributes.id,
            imageUrl = it.images[2].imageUrl,
            title = it.title.name
        )
    }
}