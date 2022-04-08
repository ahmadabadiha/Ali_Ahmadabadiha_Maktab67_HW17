package com.example.hw17.uilayer

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.example.hw17.databinding.FragmentPlayVideoBinding
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem


class PlayVideoFragment : Fragment() {
    private var _binding: FragmentPlayVideoBinding? = null
    private val binding get() = _binding!!
    private var player: ExoPlayer? = null
    private lateinit var activityResultLauncher: ActivityResultLauncher<Array<String>>
    private lateinit var uri: Uri
    private var playsWhenReady = true
    private var currentWindow = 0
    private var playbackPosition = 0L
    private var isFirstRun = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            activityResultLauncher = registerForActivityResult(ActivityResultContracts.OpenDocument()) {
                uri = it
                playVideo()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        if (isFirstRun) {
            isFirstRun = false
            activityResultLauncher.launch(arrayOf("video/*"))
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
        val mediaItem: MediaItem = MediaItem.fromUri(uri)
        player!!.setMediaItem(mediaItem)
        player!!.playWhenReady = playsWhenReady
        player!!.seekTo(currentWindow, playbackPosition)
        player!!.prepare()
        player!!.play()
    }

    override fun onStop() {
        player?.run {
            playbackPosition = this.currentPosition
            currentWindow = this.currentMediaItemIndex
            playsWhenReady = this.playWhenReady
            //isFirstRun = false
        }
        player = null
        super.onStop()
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}