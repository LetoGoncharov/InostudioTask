package com.example.attempt012.moviepage.photos.drawing.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.attempt012.R
import com.example.attempt012.databinding.PhotoItemBinding
import com.example.attempt012.moviepage.photos.drawing.model.PhotoModel
import com.squareup.picasso.Picasso

class PhotoAdapter: RecyclerView.Adapter<PhotoAdapter.PhotoHolder>() {

    private var photoList = ArrayList<PhotoModel>()

    class PhotoHolder(photo: View) : RecyclerView.ViewHolder(photo) {

        val binding = PhotoItemBinding.bind(photo)

        fun bind(pic: PhotoModel) = with(binding) {
            Picasso.get()
                .load("https://image.tmdb.org/t/p/original${pic.pic}")
                .into(photoHolder)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.photo_item, parent, false)
        return PhotoHolder(view)
    }

    override fun onBindViewHolder(holder: PhotoHolder, position: Int) {
        holder.bind(photoList[position])
    }

    override fun getItemCount(): Int {
        return photoList.size
    }

    fun addPhoto(photo: PhotoModel) {
        photoList.add(photo)
        notifyDataSetChanged()
    }
}