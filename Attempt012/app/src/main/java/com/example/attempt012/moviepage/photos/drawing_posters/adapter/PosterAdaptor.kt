package com.example.attempt012.moviepage.photos.drawing.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.attempt012.R
import com.example.attempt012.databinding.PosterItemBinding
import com.example.attempt012.moviepage.photos.drawing.model.PosterModel
import com.squareup.picasso.Picasso

class PosterAdapter: RecyclerView.Adapter<PosterAdapter.PosterHolder>() {

    private var posterList = ArrayList<PosterModel>()

    class PosterHolder(photo: View) : RecyclerView.ViewHolder(photo) {

        val binding = PosterItemBinding.bind(photo)

        fun bind(pic: PosterModel) = with(binding) {
            Picasso.get()
                .load("https://image.tmdb.org/t/p/original${pic.pic}")
                .into(photoHolder)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PosterHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.poster_item, parent, false)
        return PosterHolder(view)
    }

    override fun onBindViewHolder(holder: PosterHolder, position: Int) {
        holder.bind(posterList[position])
    }

    override fun getItemCount(): Int {
        return posterList.size
    }

    fun addPoster(photo: PosterModel) {
        posterList.add(photo)
        notifyDataSetChanged()
    }
}