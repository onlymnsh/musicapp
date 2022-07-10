package com.example.practicaltestapplication.di

import com.example.practicaltestapplication.domain.repository.SongRepository
import com.example.practicaltestapplication.data.repository.SongsRepositoryImpl
import com.example.practicaltestapplication.data.source.LocalDataSource
import com.example.practicaltestapplication.data.source.RemoteDataSource
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindSongRepository(
        songRepositoryImpl: SongsRepositoryImpl
    ): SongRepository

}