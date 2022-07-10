package com.example.practicaltestapplication.domain.repository

import com.example.practicaltestapplication.domain.model.SongData

interface SongRepository {

    suspend fun getSongData(): Resource<List<SongData>>

    suspend fun getSongById(id: String): Resource<SongData>
}