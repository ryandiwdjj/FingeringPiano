package RecyclerAdapter

import Models.video
import API.ApiClient
import android.content.Context
import android.provider.MediaStore
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.example.fingeringpiano.R
import com.squareup.picasso.Picasso

class VideoAdapter(private val items: List<video>, val context: Context) : RecyclerView.Adapter<ViewHolder>() {

    var onItemClick: ((video) -> Unit)? = null
    var videos: List<video> = emptyList()

    override fun onCreateViewHolder(viewGroup: ViewGroup, p1: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.recycler_video, viewGroup, false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, pos: Int) {
        holder.onBind(items[pos])
    }
}

class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {
    private val video_thumbnail = view.findViewById<ImageView>(R.id.video_thumb_pict)
    private val video_name = view.findViewById<TextView>(R.id.video_name_txt)
    private val garis = view.findViewById<ImageView>(R.id.line)

    fun onBind(video: video) {
        Picasso.get().load(ApiClient.getThumbnail() + video.thumbnail).resize(150, 150)
            .centerCrop().placeholder(R.drawable.ic_notes_black).into(video_thumbnail)
        Log.d("onBind", ApiClient.getThumbnail() + video.thumbnail)
        video_name.text = video.name

        if(video.category_id == 1) { //pemula
            Picasso.get().load(R.color.trueGreen).into(garis)
        }
        else if(video.category_id == 2) { //medium
            Picasso.get().load(R.color.trueYellow).into(garis)
        }
        else if(video.category_id == 3) { //expert
            Picasso.get().load(R.color.trueRed).into(garis)
        }
    }

}