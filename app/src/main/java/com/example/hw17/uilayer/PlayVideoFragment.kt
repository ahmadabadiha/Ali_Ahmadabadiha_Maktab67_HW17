package com.example.hw17.uilayer

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.hw17.databinding.FragmentPlayVideoBinding
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem


class PlayVideoFragment : Fragment() {
    private var _binding: FragmentPlayVideoBinding? = null
    private val binding get() = _binding!!
    private var player: ExoPlayer? = null
    private val viewModel: PlayVideoViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            viewModel.activityResultLauncher = registerForActivityResult(ActivityResultContracts.OpenDocument()) {
                viewModel.uri = it
                playVideo()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        if (viewModel.isFirstRun) {
            viewModel.isFirstRun = false
            viewModel.activityResultLauncher.launch(arrayOf("video/*"))
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPlayVideoBinding.inflate(layoutInflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (savedInstanceState != null) playVideo()
    }

    private fun playVideo() {
        player = ExoPlayer.Builder(requireContext()).build()
        binding.playerView.player = player
        val mediaItem: MediaItem = MediaItem.fromUri(viewModel.uri)
        player!!.setMediaItem(mediaItem)
        player!!.playWhenReady = viewModel.playsWhenReady
        player!!.seekTo(viewModel.currentWindow, viewModel.playbackPosition)
        player!!.prepare()
        player!!.play()
    }

    override fun onStop() {
        player?.run {
            viewModel.playbackPosition = currentPosition
            viewModel.currentWindow = currentMediaItemIndex
            viewModel.playsWhenReady = playWhenReady
        }
        super.onStop()
    }

    override fun onDestroy() {
        _binding = null
        player!!.release()
        player=null
        super.onDestroy()
    }
}