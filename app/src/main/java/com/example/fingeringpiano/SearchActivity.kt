package com.example.fingeringpiano

import API.ApiClient
import API.VideoInterface
import Models.video
import RecyclerAdapter.VideoAdapter
import RecyclerAdapter.addOnItemClickListener
import RecyclerAdapter.onItemClickListener
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        var sp  = getSharedPreferences("login", Context.MODE_PRIVATE)
        var back_btn = findViewById<ImageButton>(R.id.back_btn)
        var search_etxt = findViewById<EditText>(R.id.search_etxt)
        var search_btn = findViewById<ImageButton>(R.id.search_btn)
        var apiInterface = ApiClient.getApiClient().create(VideoInterface::class.java)
        var video_recycler = findViewById<RecyclerView>(R.id.video_recycler)
        var video_list  = ArrayList<video>()
        var video_adapter = VideoAdapter(video_list, applicationContext)

        var spinkit = findViewById<View>(R.id.spin_kit)
        spinkit.visibility = View.VISIBLE

        //video_recycler.layoutManager = LinearLayoutManager(applicationContext)
        //video_recycler.itemAnimator = DefaultItemAnimator()
        //video_recycler.adapter = video_adapter

        back_btn.setOnClickListener{
            finish()
        }

        search_etxt.requestFocus()

        var token = "bearer" + sp.getString("token", null)
        Log.d("tag", token)

        var call = apiInterface.indexVideo(token)
        call.enqueue(object: Callback<ArrayList<video>> {
            override fun onFailure(call: Call<ArrayList<video>>, t: Throwable) {
                Log.e("onFailure", t.message)
                spinkit.visibility = View.INVISIBLE
            }

            override fun onResponse(call: Call<ArrayList<video>>, response: Response<ArrayList<video>>) {
                spinkit.visibility = View.INVISIBLE
                if (response.isSuccessful) {

                    video_list = response.body() as ArrayList<video>
                    Log.d("isSuccessful", video_list.toString())

//                    video_recycler.layoutManager = LinearLayoutManager(this@MainActivity)
//                    video_recycler.layoutManager = LinearLayoutManager(this@SearchActivity, LinearLayoutManager.HORIZONTAL, false)
//                    video_adapter = VideoAdapter(video_list, applicationContext)
//                    video_recycler.adapter = video_adapter
                }
                else {
                    Log.d("isFailure", response.message())
                }
            }
        })

        search_btn.setOnClickListener {

            var view = this.getCurrentFocus();
            if (view != null) {
                var imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0)
            }

            search_etxt.clearFocus()

            video_adapter = VideoAdapter(video_list, applicationContext)
            video_adapter.nameFilter().filter(search_etxt.text.toString())

            video_recycler.layoutManager = LinearLayoutManager(this@SearchActivity, LinearLayoutManager.HORIZONTAL, false)
            video_recycler.adapter = video_adapter
        }

        video_recycler.addOnItemClickListener(object: onItemClickListener {
            override fun onItemClicked(position: Int, view: View) {
                // Your logic
                Log.d("recycler clicked", video_list.get(position).name)

                var i = Intent(this@SearchActivity, VideoActivity::class.java)
                i.putExtra("video", video_list.get(position).fileUrl)

                startActivity(i)
            }
        })
    }
}
