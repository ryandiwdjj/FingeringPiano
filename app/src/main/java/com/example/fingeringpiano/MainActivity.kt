package com.example.fingeringpiano

import API.ApiClient
import API.UserInterface
import API.VideoInterface
import DialogFragment.DetailUserDialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import Models.video
import RecyclerAdapter.VideoAdapter
import android.content.Context
import android.content.Intent
import android.nfc.Tag
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.support.v7.widget.DefaultItemAnimator




class MainActivity : AppCompatActivity() {
    var fm = supportFragmentManager
    var video_list  = ArrayList<video>()
//    var apiInterface = ApiClient().getApiClient().create(VideoInterface::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //login token handler
        var login_token = "null"
        var sp  = getSharedPreferences("login", Context.MODE_PRIVATE)
        var videoAdapter : VideoAdapter

        var user_btn = findViewById<ImageButton>(R.id.user_btn)
        user_btn.setOnClickListener {
            Log.d("user_btn", "clicked")

            var dialog = DetailUserDialog()

            val bundle = Bundle()
            bundle.putString("data", login_token)

            dialog.arguments = bundle
            dialog.show(fm, "dialog")
        }

        var search_btn = findViewById<ImageButton>(R.id.search_btn)
        search_btn.setOnClickListener{
//            Toast.makeText(applicationContext, "Search Button Pressed", Toast.LENGTH_LONG).show()
            startActivity(Intent(this, SearchActivity::class.java))
        }

        var video_recycler = findViewById<RecyclerView>(R.id.video_recycler)
        video_recycler.setLayoutManager(LinearLayoutManager(applicationContext))
        video_recycler.setItemAnimator(DefaultItemAnimator())
        video_recycler.setAdapter(VideoAdapter(video_list, applicationContext))

        var token = "bearer" + sp.getString("token", null)
        Log.d("tag", token)

        var apiInterface = ApiClient().getApiClient().create(VideoInterface::class.java)

        var call = apiInterface.indexVideo(token)
        call.enqueue(object: Callback<List<video>> {
            override fun onFailure(call: Call<List<video>>, t: Throwable) {
                Log.e("onFailure", t.message)
            }

            override fun onResponse(call: Call<List<video>>, response: Response<List<video>>) {
                if (response.isSuccessful) {
                    Log.d("isSuccessful", response.body().toString())

                    video_list = response.body() as ArrayList<video>

                    video_recycler.layoutManager = LinearLayoutManager(this@MainActivity)
                    video_recycler.adapter = VideoAdapter(video_list, applicationContext)
                }
                else {
                    Log.d("isFailure", response.message())
                }
            }
        })

    }
}
