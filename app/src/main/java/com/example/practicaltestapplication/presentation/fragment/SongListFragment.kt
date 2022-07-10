package com.example.practicaltestapplication.presentation.fragment

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
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.practicaltestapplication.databinding.FragmentSongListBinding
import com.example.practicaltestapplication.domain.model.SongData
import com.example.practicaltestapplication.presentation.adapter.ItemClickListener
import com.example.practicaltestapplication.presentation.adapter.SongListAdapter
import com.example.practicaltestapplication.presentation.viewmodel.SongListViewModel
import com.example.practicaltestapplication.presentation.viewmodel.SongState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SongListFragment : Fragment(), ItemClickListener {
    private var _binding: FragmentSongListBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<SongListViewModel>()
    private val songAdapter by lazy { SongListAdapter(this) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSongListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvSongs.adapter = songAdapter
        binding.rvSongs.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                DividerItemDecoration.VERTICAL
            )
        )
        onSubscribe()
    }

    private fun onSubscribe() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect {
                    when (it) {
                        is SongState.Idle -> Unit
                        is SongState.Loading -> {
                            binding.progressbar.visibility = View.VISIBLE
                        }
                        is SongState.Loaded -> {
                            binding.progressbar.visibility = View.GONE
                            songAdapter.submitList(it.songs)
                        }
                        is SongState.Failure -> {
                            binding.progressbar.visibility = View.GONE
                            Toast.makeText(requireContext(), it.exception, Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClick(songData: SongData) {
        val action =
            SongListFragmentDirections.actionSongListFragmentToSongDetailsFragment(songData.id)
        findNavController().navigate(action)
    }
}