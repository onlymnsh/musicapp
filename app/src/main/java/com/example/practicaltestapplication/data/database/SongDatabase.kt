package com.example.practicaltestapplication.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.practicaltestapplication.data.model.SongDto
import com.example.practicaltestapplication.domain.model.SongData

@Database(
    entities = [SongData::class],
    version = 1
)
abstract class SongDatabase : RoomDatabase() {
    abstract val songDao: SongDao

    companion object {
        const val DATABASE_NAME = "songs_db"
    }
}