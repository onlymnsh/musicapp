package com.example.practicaltestapplication.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.practicaltestapplication.domain.model.SongData
import com.example.practicaltestapplication.domain.repository.Resource
import com.example.practicaltestapplication.domain.repository.SongRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class SongState {
    object Idle : SongState()
    object Loading : SongState()
    class Loaded(val songs: List<SongData>?) : SongState()
    class Failure(val exception: String?) : SongState()
}

@HiltViewModel
class SongListViewModel @Inject constructor(
    private val repository: SongRepository
) : ViewModel() {

    var state = MutableStateFlow<SongState>(SongState.Idle)
        private set


    init {
        fetchSongs()
    }

    private fun fetchSongs() {
        viewModelScope.launch(Dispatchers.IO) {
            state.update { SongState.Loading }
            when (val result = repository.getSongData()) {
                is Resource.Success -> {
                    state.update { SongState.Loaded(result.data) }
                }
                is Resource.Error -> {
                    state.update { SongState.Failure(result.message) }
                }
            }
        }
    }
}