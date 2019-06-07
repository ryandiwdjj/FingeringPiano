package com.example.fingeringpiano

import API.ApiClient
import API.UserInterface
import API.VideoInterface
import DialogFragment.DetailUserDialog
import Models.category
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import Models.video
import RecyclerAdapter.VideoAdapter
import android.content.Context
import android.content.Intent
import android.nfc.Tag
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.support.v7.widget.DefaultItemAnimator
import android.view.Gravity
import android.view.View
import android.widget.*
import org.json.JSONArray
import org.json.JSONObject


class MainActivity : AppCompatActivity() {
    var fm = supportFragmentManager
    var video_list  = ArrayList<video>()
    var apiInterface = ApiClient.getApiClient().create(VideoInterface::class.java)

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

        var category_list = arrayListOf("Semua")

        var category_spinner = findViewById<Spinner>(R.id.category_spinner)
        var call_cat = apiInterface.indexCategory("bearer" + sp.getString("token", null))
        call_cat.enqueue(object : Callback<ArrayList<category>>{
            override fun onFailure(call: Call<ArrayList<category>>, t: Throwable) {
                Log.e("onFailure", t.message)
            }

            override fun onResponse(call: Call<ArrayList<category>>, response: Response<ArrayList<category>>) {
                if(response.isSuccessful) {
                    Log.d("isSuccessful", response.body().toString())
                    var response_size = response.body()?.size

                    Log.d("array", response_size.toString())

                    for (i in 0 until response_size!!) {
                        category_list.add(response.body()!!.get(i).name)
                    }
                }
                else {
                    Log.d("isFailure", response.body().toString())
                }
            }
        })
        category_spinner.adapter = ArrayAdapter(this, R.layout.spinner_item, category_list)
        category_spinner.gravity = Gravity.CENTER
        category_spinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                Log.d("onItemSelected spinner", "clicked")
                //Toast.makeText(this@MainActivity, category_list.get(position), Toast.LENGTH_LONG).show()
            }
        }

        var search_btn = findViewById<ImageButton>(R.id.search_btn)
        search_btn.setOnClickListener{
//            Toast.makeText(applicationContext, "Search Button Pressed", Toast.LENGTH_LONG).show()
            startActivity(Intent(this, SearchActivity::class.java))
        }

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        var video_recycler = findViewById<RecyclerView>(R.id.video_recycler)
        video_recycler.layoutManager = LinearLayoutManager(applicationContext)
        video_recycler.itemAnimator = DefaultItemAnimator()
        video_recycler.adapter = VideoAdapter(video_list, applicationContext)

        var token = "bearer" + sp.getString("token", null)
        Log.d("tag", token)

        var call = apiInterface.indexVideo(token)
        call.enqueue(object: Callback<ArrayList<video>> {
            override fun onFailure(call: Call<ArrayList<video>>, t: Throwable) {
                Log.e("onFailure", t.message)
            }

            override fun onResponse(call: Call<ArrayList<video>>, response: Response<ArrayList<video>>) {
                if (response.isSuccessful) {

                    video_list = response.body() as ArrayList<video>
                    Log.d("isSuccessful", video_list.toString())

//                    video_recycler.layoutManager = LinearLayoutManager(this@MainActivity)
                    video_recycler.layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
                    video_recycler.adapter = VideoAdapter(video_list, applicationContext)
                }
                else {
                    Log.d("isFailure", response.message())
                }
            }
        })

    }
}
