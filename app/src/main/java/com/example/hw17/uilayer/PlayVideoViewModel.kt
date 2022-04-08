package com.example.hw17.uilayer

import android.net.Uri
import androidx.activity.result.ActivityResultLauncher
import androidx.lifecycle.ViewModel

class PlayVideoViewModel : ViewModel() {
    var playsWhenReady = true
    var currentWindow = 0
    var playbackPosition = 0L
    var uri = Uri.EMPTY
    lateinit var activityResultLauncher: ActivityResultLauncher<Array<String>>
    var isFirstRun = true
}