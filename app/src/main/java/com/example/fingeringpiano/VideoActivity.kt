package com.example.fingeringpiano

import android.content.DialogInterface
import android.media.session.MediaSession
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.widget.MediaController
import android.widget.Toast
import android.widget.VideoView
import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.KeyEvent
import android.view.KeyEvent.KEYCODE_BACK



class VideoActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video)

        var url = intent.getStringExtra("video")
        var vide_sp  = getSharedPreferences("video", Context.MODE_PRIVATE)

        var video_view = findViewById<VideoView>(R.id.video_view)

        var uri = Uri.parse(url)
        video_view.setVideoURI(uri)

        var mediaController = MediaController(this)
        video_view.setMediaController(mediaController)
        mediaController.setAnchorView(video_view)

        video_view.setOnCompletionListener {
            Toast.makeText(applicationContext, "Video sudah selesai", Toast.LENGTH_LONG).show()
            vide_sp.edit().putString("id", "null").apply()
            vide_sp.edit().putString("name", "null").apply()

            Log.d("after video",vide_sp.getString("name", null))
        }
    }
}