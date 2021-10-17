package com.example.attempt012.discovery.drawing.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.attempt012.R
import com.example.attempt012.databinding.MovieItemBinding
import com.example.attempt012.discovery.drawing.model.DiscoveryDataModel
import com.example.attempt012.fragments.MoviePageFragment
import com.squareup.picasso.Picasso

class DiscoveryAdapter: RecyclerView.Adapter<DiscoveryAdapter.MovieHolder>() {

    private var movieList = ArrayList<DiscoveryDataModel>()
    private var moreInfo : ActivityResultLauncher<Intent>? = null

    class MovieHolder(item: View): RecyclerView.ViewHolder(item) {

        val binding = MovieItemBinding.bind(item)

        fun bind(movie: DiscoveryDataModel) = with(binding){
            nameMovie.text = movie.movieTitle
            releaseMovie.text = movie.movieReleaseDate
            Picasso.get()
                .load("https://image.tmdb.org/t/p/original${movie.movieCover}")
                .into(coverPlace)

            var overview = ""
            if (movie.movieOverview.length > 160) {
                var counter = 0
                while (counter < 160) {
                    overview += movie.movieOverview[counter]
                    counter++
                }
                overview += "..."
            } else overview = movie.movieOverview
            overviewMovie.text = overview
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.movie_item, parent, false)
        return MovieHolder(view)
    }

    override fun onBindViewHolder(holder: MovieHolder, position: Int) {
        var pos = position
        holder.bind(movieList[position])
        holder.itemView.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                val activ = v!!.context as AppCompatActivity
                activ.supportActionBar?.setDisplayHomeAsUpEnabled(true)
                activ.supportActionBar?.subtitle = movieList[pos].movieTitle
                activ.supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.main_content_place, MoviePageFragment(movieList[pos].movieID))
                    .commit()
            }
        })
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    fun addMovie(movie: DiscoveryDataModel) {
        movieList.add(movie)
        notifyDataSetChanged()
    }

    fun deleteData() {
        movieList = ArrayList<DiscoveryDataModel>()
        notifyDataSetChanged()
    }
}