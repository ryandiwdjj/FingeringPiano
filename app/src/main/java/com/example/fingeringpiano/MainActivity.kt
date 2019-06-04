package com.example.fingeringpiano

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import DialogFragment.LoginDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.support.v4.app.FragmentManager
import android.widget.Toast
import java.lang.Exception


class MainActivity : AppCompatActivity() {
    val fm = supportFragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //login token handler
        var login_token: String
        var sp  = getSharedPreferences("login", Context.MODE_PRIVATE)

        try {
            login_token = sp.getString("token", null)
        }
        catch(e: Throwable) {
            var ed: SharedPreferences.Editor = sp.edit()
            ed.putString("token", "null")
            ed.apply()

            login_token = sp.getString("token", null)
        }
        Log.d("login_token", login_token)


        val user_btn = findViewById<ImageButton>(R.id.user_btn)
        user_btn.setOnClickListener {
            Log.d("user_btn", "clicked")

            var dialog = LoginDialog()
            dialog.show(fm, "dialog")
        }

        val search_btn = findViewById<ImageButton>(R.id.search_btn)
        search_btn.setOnClickListener{
//            Toast.makeText(applicationContext, "Search Button Pressed", Toast.LENGTH_LONG).show()
            startActivity(Intent(this, SearchActivity::class.java))
        }

        val video_btn = findViewById<Button>(R.id.video_btn)
        video_btn.setOnClickListener {
            startActivity(Intent(this, VideoActivity::class.java))
        }
    }


    fun onDialogResult() {
        var sp  = getSharedPreferences("login", Context.MODE_PRIVATE)
        Log.d("login_token", sp.getString("token", null))
    }
}
