package com.fingering.fingeringpiano

import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.widget.MediaController
import android.widget.Toast
import android.widget.VideoView
import android.content.Context
import android.util.Log
import android.view.View


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

        var spinkit = findViewById<View>(R.id.spin_kit)
        spinkit.visibility = View.VISIBLE

        video_view.setOnPreparedListener {
            mediaController.show(5000)

            Toast.makeText(applicationContext, "Video berhasil di load!", Toast.LENGTH_LONG).show()
            spinkit.visibility = View.INVISIBLE
        }

        video_view.setOnCompletionListener {
            vide_sp.edit().putString("id", "null").apply()
            vide_sp.edit().putString("name", "null").apply()

            var dialog = AlertDialog.Builder(this)
            dialog.setTitle("Video sudah selesai")
            dialog.setMessage("Tutup Pemutar Video?")
                .setPositiveButton("ya", {
                        dialog, id -> super.onBackPressed()
                })
                .setNegativeButton("Tidak", {
                        dialog, id -> dialog.cancel()
                })
                .show()

            Log.d("after video",vide_sp.getString("name", null))
        }
    }

    override fun onBackPressed() {
        var dialog = AlertDialog.Builder(this)
        dialog.setTitle("Video belum selesai")
        dialog.setMessage("Tutup Pemutar Video?")
            .setPositiveButton("ya", {
                dialog, id -> super.onBackPressed()
            })
            .setNegativeButton("Tidak", {
                dialog, id -> dialog.cancel()
            })
            .show()
    }
}