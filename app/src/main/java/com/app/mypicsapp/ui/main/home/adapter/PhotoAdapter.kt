package com.app.mypicsapp.ui.main.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.mypicsapp.R
import com.app.mypicsapp.data.model.ListOfPhotos
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import kotlinx.android.synthetic.main.row_layout.view.*

class PhotoAdapter(
    private val photos: ArrayList<ListOfPhotos>,
    private val itemClickListener: OnItemClickListener
) : RecyclerView.Adapter<PhotoAdapter.DataViewHolder>() {

    class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(photo: ListOfPhotos, clickListener: OnItemClickListener) {
            itemView.apply {
                Glide.with(photoView.context)
                    .load(photo.previewURL)
                    .placeholder(R.drawable.ic_loader)
                    .error(R.drawable.ic_error)
                    .diskCacheStrategy(DiskCacheStrategy.DATA)
                    .into(photoView)
            }

            itemView.authorName.text = "Credits: "+photo.user

            itemView.setOnClickListener {
                clickListener.onItemClick(photo)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder =
        DataViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.row_layout, parent, false)
        )

    override fun getItemCount(): Int = photos.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(photo = photos[position], clickListener = itemClickListener)
    }

    fun addPhotos(photo: ArrayList<ListOfPhotos>) {
        this.photos.apply {
            addAll(photo)
        }
    }

    fun clearPhotos() {
        this.photos.apply {
            photos.clear()
        }
    }

    interface OnItemClickListener {
        fun onItemClick(item: ListOfPhotos)
    }

}