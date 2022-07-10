package com.example.practicaltestapplication.presentation.fragment

import android.content.ComponentName
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.session.MediaController
import androidx.media3.session.SessionToken
import androidx.media3.ui.PlayerView
import com.example.practicaltestapplication.databinding.FragmentSongDetailBinding
import com.example.practicaltestapplication.domain.model.SongData
import com.example.practicaltestapplication.presentation.player.PlaybackService
import com.example.practicaltestapplication.presentation.viewmodel.SongDetailsState
import com.example.practicaltestapplication.presentation.viewmodel.SongDetailsViewModel
import com.example.practicaltestapplication.utils.loadPreviewImage
import com.google.common.util.concurrent.ListenableFuture
import com.google.common.util.concurrent.MoreExecutors
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SongDetailsFragment : Fragment() {
    private var _binding: FragmentSongDetailBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<SongDetailsViewModel>()

    private lateinit var controllerFuture: ListenableFuture<MediaController>
    private val controller: MediaController?
        get() = if (controllerFuture.isDone) controllerFuture.get() else null

    private lateinit var playerView: PlayerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSongDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        playerView = binding.playerView
        onSubscribe()
    }

    private fun onSubscribe() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect {
                    when (it) {
                        is SongDetailsState.Idle -> Unit
                        is SongDetailsState.Loading -> Unit
                        is SongDetailsState.Loaded -> {
                            setSongDetails(it.song)
                        }
                        is SongDetailsState.Failure -> {
                            Toast.makeText(requireContext(), it.exception, Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }
        }
    }

    private fun setSongDetails(song: SongData?) {
        song?.let {
            loadPreviewImage(binding.songPreview, it.imageUrl)
            binding.title.text = it.title
        }
    }

    override fun onStart() {
        super.onStart()
        initializeController()
    }

    override fun onResume() {
        super.onResume()
        playerView.onResume()
    }

    override fun onPause() {
        super.onPause()
        playerView.onPause()
    }

    override fun onStop() {
        super.onStop()
        playerView.player = null
        releaseController()
    }

    private fun initializeController() {
        controllerFuture =
            MediaController.Builder(
                requireContext(),
                SessionToken(
                    requireContext(),
                    ComponentName(requireContext(), PlaybackService::class.java)
                )
            )
                .buildAsync()
        controllerFuture.addListener({ setController() }, MoreExecutors.directExecutor())
    }

    private fun releaseController() {
        MediaController.releaseFuture(controllerFuture)
    }

    private fun setController() {
        val controller = this.controller ?: return

        playerView.player = controller

        controller.addListener(
            object : Player.Listener {
                override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {
                }

                override fun onShuffleModeEnabledChanged(shuffleModeEnabled: Boolean) {
                }

                override fun onRepeatModeChanged(repeatMode: Int) {
                }
            }
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}