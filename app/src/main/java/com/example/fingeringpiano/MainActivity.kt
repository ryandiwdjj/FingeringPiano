package com.example.fingeringpiano

import API.ApiClient
import API.VideoInterface
import com.example.fingeringpiano.DialogFragment.DetailUserDialog
import com.example.fingeringpiano.Models.category
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.fingeringpiano.Models.video
import com.example.fingeringpiano.RecyclerAdapter.VideoAdapter
import com.example.fingeringpiano.RecyclerAdapter.addOnItemClickListener
import com.example.fingeringpiano.RecyclerAdapter.onItemClickListener
import android.content.Context
import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.support.v7.widget.DefaultItemAnimator
import android.view.Gravity
import android.view.View
import android.widget.*
import android.support.v7.app.AlertDialog


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var fm = supportFragmentManager
        var video_list  = ArrayList<video>()
        var apiInterface = ApiClient.getApiClient().create(VideoInterface::class.java)
        var video_recycler = findViewById<RecyclerView>(R.id.video_recycler)
        var video_adapter = VideoAdapter(video_list, applicationContext)

        var spinkit = findViewById<View>(R.id.spin_kit)
        spinkit.visibility = View.VISIBLE

        //login token handler
        var login_token = "null"
        var sp  = getSharedPreferences("login", Context.MODE_PRIVATE)
        var video_sp  = getSharedPreferences("video", Context.MODE_PRIVATE)

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

        var search_btn = findViewById<ImageButton>(R.id.search_btn)
        search_btn.setOnClickListener{
            //            Toast.makeText(applicationContext, "Search Button Pressed", Toast.LENGTH_LONG).show()
            startActivity(Intent(this, SearchActivity::class.java))
        }

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        //var video_recycler = findViewById<RecyclerView>(R.id.video_recycler)
        video_recycler.layoutManager = LinearLayoutManager(applicationContext)
        video_recycler.itemAnimator = DefaultItemAnimator()
        video_recycler.adapter = VideoAdapter(video_list, applicationContext)

        var token = "bearer" + sp.getString("token", null)
        Log.d("tag", token)

        var call = apiInterface.indexVideo(token)
        call.enqueue(object: Callback<ArrayList<video>> {
            override fun onFailure(call: Call<ArrayList<video>>, t: Throwable) {
                spinkit.visibility = View.INVISIBLE
                Log.e("onFailure", t.message)
            }

            override fun onResponse(call: Call<ArrayList<video>>, response: Response<ArrayList<video>>) {
                spinkit.visibility = View.INVISIBLE
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

        video_recycler.addOnItemClickListener(object: onItemClickListener {
            override fun onItemClicked(position: Int, view: View) {
                // Your logic
                Log.d("video id",video_sp.getString("id", null))
                Log.d("video name",video_sp.getString("name", null))

                if(video_sp.getString("id", null).equals(video_list.get(position).fileId) ||
                    video_sp.getString("id", null).equals("null")) {

                    var i = Intent(this@MainActivity, VideoActivity::class.java)
                    i.putExtra("video", video_list.get(position).fileUrl)

                    video_sp.edit().putString("id", video_list.get(position).fileId).apply()
                    video_sp.edit().putString("name", video_list.get(position).name).apply()

                    startActivity(i)
                }
                else {
                    Toast.makeText(applicationContext,
                        "Video" + video_sp.getString("name ", null) + " belum selesai ditonton",
                        Toast.LENGTH_LONG).show()
                }
            }
        })

        ////////////////////////////////////////////////////////////////////////////////////////////////

        category_spinner.adapter = ArrayAdapter(this, R.layout.spinner_item, category_list) as SpinnerAdapter?
        category_spinner.gravity = Gravity.CENTER
        category_spinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                Log.d("onItemSelected spinner", category_list.get((position)))
                //Toast.makeText(this@MainActivity, category_list.get(position), Toast.LENGTH_LONG).show()

                if(position == 0) {
                    video_recycler.layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
                    video_recycler.adapter = VideoAdapter(video_list, applicationContext)
                }
                else {
                    video_adapter = VideoAdapter(video_list, applicationContext)
                    video_adapter.categoryFilter().filter(category_list.get((position)))

                    video_recycler.layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
                    video_recycler.adapter = video_adapter
                }
            }
        }
    }

    override fun onBackPressed() {
        val builder = AlertDialog.Builder(this@MainActivity)
        builder.setTitle("Anda yakin ingin keluar?")

        builder.setNegativeButton("Tidak", { dialog, which -> })

        builder.setPositiveButton("Ya", { dialog, which ->
            finish()
            System.exit(0)
            //                    MainActivity.super.onBackPressed();
        })
        builder.show()
    }
}

