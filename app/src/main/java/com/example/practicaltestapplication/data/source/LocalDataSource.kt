package com.example.practicaltestapplication.data.source

import android.util.Log
import com.example.practicaltestapplication.data.database.SongDao
import com.example.practicaltestapplication.domain.model.SongData
import com.example.practicaltestapplication.domain.repository.Resource
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val dao: SongDao
) {
    fun isDataExist(): Boolean {
        return dao.isExists()
    }

    suspend fun getSongList(): Resource<List<SongData>> {
        return try {
            Resource.Success(
                data = dao.getSongs()
            )
        } catch (e: Exception) {
            Log.e("SongsApp", "FetchSongs", e)
            Resource.Error(e.message ?: "An unknown error occurred.")
        }
    }

    suspend fun insertAllSongs(songs: List<SongData>) {
        dao.insertAllSongs(songs)
    }

    suspend fun getSongById(id: String): Resource<SongData> {
        return try {
            Resource.Success(
                data = dao.getSongById(id)
            )
        } catch (e: Exception) {
            Log.e("SongsApp", "SongById", e)
            Resource.Error(e.message ?: "An unknown error occurred.")
        }
    }
}