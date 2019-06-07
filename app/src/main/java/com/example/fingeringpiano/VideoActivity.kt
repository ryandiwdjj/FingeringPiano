package com.example.fingeringpiano

import android.media.session.MediaSession
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.MediaController
import android.widget.VideoView

class VideoActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video)

        var url = intent.getStringExtra("video")

        var video_view = findViewById<VideoView>(R.id.video_view)

        var uri = Uri.parse(url)
        video_view.setVideoURI(uri)

        var mediaController = MediaController(this)
        video_view.setMediaController(mediaController)
        mediaController.setAnchorView(video_view)
    }
}