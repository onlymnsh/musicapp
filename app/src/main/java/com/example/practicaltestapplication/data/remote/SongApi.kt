package com.example.practicaltestapplication.data.remote

import com.example.practicaltestapplication.data.model.SongDto
import retrofit2.http.GET

interface SongApi {
    @GET("WebObjects/MZStoreServices.woa/ws/RSS/topsongs/limit=20/json")
    suspend fun getSongData(): SongDto
}