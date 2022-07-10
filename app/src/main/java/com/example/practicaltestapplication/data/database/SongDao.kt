package com.example.practicaltestapplication.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.practicaltestapplication.domain.model.SongData

@Dao
interface SongDao {
    @Query("SELECT EXISTS(SELECT * FROM songs)")
    fun isExists(): Boolean

    @Query("SELECT * FROM songs")
    suspend fun getSongs(): List<SongData>

    @Query("SELECT * FROM songs WHERE id = :id")
    suspend fun getSongById(id: String): SongData

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllSongs(songs: List<SongData>)

}