package com.fingering.fingeringpiano.RecyclerAdapter

import com.fingering.fingeringpiano.Models.video
import com.fingering.fingeringpiano.API.ApiClient
import android.annotation.SuppressLint
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.ImageView
import android.widget.TextView
import com.fingering.fingeringpiano.R
import com.github.ybq.android.spinkit.SpinKitView
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import java.lang.Exception

class VideoAdapter(var videos: ArrayList<video>, val context: Context) : RecyclerView.Adapter<ViewHolder>() {
    var videos_temp = videos

    override fun onCreateViewHolder(viewGroup: ViewGroup, p1: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.recycler_video, viewGroup, false))
    }

    override fun getItemCount(): Int {
        return videos.size
    }

    override fun onBindViewHolder(holder: ViewHolder, pos: Int) {
        holder.onBind(videos[pos])
    }

    fun categoryFilter() : Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                var filteredList = ArrayList<video>()
                Log.d("videos", videos_temp.toString())

                if (constraint.toString() == null || constraint.toString().length == 0) {
                    filteredList.addAll(videos)
                } else {
                    Log.d("nameFiltering", "executed")

                    var filterPattern = constraint.toString().toLowerCase().trim()
                    Log.d("filterPattern", filterPattern)

                    for (video in videos) {
                        if(video.category.name.toLowerCase().contains(filterPattern)) {
                            filteredList.add(video)
                        }
                    }
                }

                var results = FilterResults()
                results.values = filteredList
                Log.d("filteredList", filteredList.toString())

                return results
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                videos = results?.values as ArrayList<video>
                Log.d("publicResults", videos.toString())
                notifyDataSetChanged()
            }
        }
    }

    fun nameFilter() : Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                var filteredList = ArrayList<video>()
                Log.d("videos", videos_temp.toString())

                if (constraint.toString() == null || constraint.toString().length == 0) {
                    filteredList.addAll(videos)
                } else {
                    Log.d("nameFiltering", "executed")

                    var filterPattern = constraint.toString().toLowerCase().trim()
                    Log.d("filterPattern", filterPattern)

                    for (video in videos) {
                        if(video.name.toLowerCase().contains(filterPattern)) {
                        filteredList.add(video)
                         }
                    }
                }

                var results = FilterResults()
                results.values = filteredList
                Log.d("filteredList", filteredList.toString())

                return results
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                videos = results?.values as ArrayList<video>
                Log.d("publicResults", videos.toString())
                notifyDataSetChanged()
            }
        }
    }
}

class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {
    private val video_thumbnail = view.findViewById<ImageView>(R.id.video_thumb_pict)
    private val video_name = view.findViewById<TextView>(R.id.video_name_txt)
    private var garis = view.findViewById<ImageView>(R.id.line)
    private val spin_kit = view.findViewById<SpinKitView>(R.id.spin_kit)

    @SuppressLint("ResourceAsColor")
    fun onBind(video: video) {
        spin_kit.visibility = View.VISIBLE

        Picasso.get().load(ApiClient.getThumbnail() + video.thumbnail).resize(150, 150)
            .centerCrop().into(video_thumbnail, object: Callback {
                override fun onSuccess() {
                    spin_kit.visibility = View.INVISIBLE
                }

                override fun onError(e: Exception?) {
                }
            })

        video_name.text = video.name

        if(video.category_id === 1) { //pemula
            Log.d("category", "pemula")
            garis.setBackgroundColor(R.color.trueGreen)
//            Picasso.get().load(R.color.trueGreen).into(garis)
        }
        else if(video.category_id === 2) { //medium
            Log.d("category", "medium")
            garis.setBackgroundColor(R.color.trueYellow)
//            Picasso.get().load(R.color.trueYellow).into(garis)
        }
        else if(video.category_id === 3) { //expert
            Log.d("category", "expert")
            garis.setBackgroundColor(R.color.trueRed)
//            Picasso.get().load(R.color.trueRed).into(garis)
        }
        else {
            Log.d("category", "lain lain")
            garis.setBackgroundColor(R.color.broken_white)
        }
    }

}