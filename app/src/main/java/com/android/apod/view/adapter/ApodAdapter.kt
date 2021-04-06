package com.android.apod.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.apod.R
import com.android.apod.data.model.AstronomyPicture
import com.android.apod.view.ui.OnApodItemClickListener
import com.bumptech.glide.Glide

class ApodAdapter(
    context: Context,
    mAstronomyPictures: List<AstronomyPicture>?,
    listener: OnApodItemClickListener
) :
    RecyclerView.Adapter<ApodAdapter.ApodViewholder>() {
    private val context = context
    private val mAstronomyPictures = mAstronomyPictures
    private val listener = listener

    class ApodViewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        open val itemTitle: TextView = itemView.findViewById(R.id.apod_item_title)
        val itemImage: ImageView = itemView.findViewById(R.id.apod_item_image)
        val itemDate: TextView = itemView.findViewById(R.id.apod_item_date)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ApodViewholder {
        val view = LayoutInflater.from(context).inflate(R.layout.apod_view, parent, false)
        val holder = ApodViewholder(view)
        return holder
    }

    override fun onBindViewHolder(holder: ApodViewholder, position: Int) {
        if (mAstronomyPictures != null) {
            val item = mAstronomyPictures[position]!!
            holder.itemTitle.text = item.title
            holder.itemDate.text = item.date
            if (item.media_type.equals("image")) {
                Glide.with(context).load(item.url).into(holder.itemImage)
            }
            holder.itemView.setOnClickListener {
                listener.onItemOpen(item)
            }
        }
    }

    override fun getItemCount(): Int {
        if (mAstronomyPictures == null) {
            return 0
        } else {
            return mAstronomyPictures.size
        }
    }
}