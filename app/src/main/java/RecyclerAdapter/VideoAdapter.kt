package RecyclerAdapter

import Models.video
import android.content.Context
import android.provider.MediaStore
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import com.example.fingeringpiano.R
import kotlinx.android.synthetic.main.recycler_video.view.*

class VideoAdapter(private val items: List<video>, val context: Context) : RecyclerView.Adapter<ViewHolder>() {

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
    private val video_name = view.video_name_txt

    fun onBind(video: video) {
        video_name.text = video.name
    }
}