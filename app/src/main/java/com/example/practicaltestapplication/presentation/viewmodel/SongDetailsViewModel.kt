package com.example.practicaltestapplication.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
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

sealed class SongDetailsState {
    object Idle : SongDetailsState()
    object Loading : SongDetailsState()
    class Loaded(val song: SongData?) : SongDetailsState()
    class Failure(val exception: String?) : SongDetailsState()
}

@HiltViewModel
class SongDetailsViewModel @Inject constructor(
    private val repository: SongRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    var state = MutableStateFlow<SongDetailsState>(SongDetailsState.Idle)
        private set

    init {
        val id = savedStateHandle.get<String>("id")
        id?.let { getSongDetailsById(id) }
    }

    private fun getSongDetailsById(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            state.update { SongDetailsState.Loading }
            when (val result = repository.getSongById(id)) {
                is Resource.Success -> {
                    state.update { SongDetailsState.Loaded(result.data) }
                }
                is Resource.Error -> {
                    state.update { SongDetailsState.Failure(result.message) }
                }
            }
        }
    }
}