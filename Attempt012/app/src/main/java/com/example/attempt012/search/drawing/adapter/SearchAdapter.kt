package com.example.attempt012.search.drawing.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.attempt012.CheckPageFragment
import com.example.attempt012.R
import com.example.attempt012.databinding.MovieItemBinding
import com.example.attempt012.fragments.MoviePageFragment
import com.example.attempt012.search.drawing.model.SearchDataModel
import com.squareup.picasso.Picasso

class SearchAdapter: RecyclerView.Adapter<SearchAdapter.FilmHolder>() {



    private var filmList = ArrayList<SearchDataModel>()

    class FilmHolder(view: View): RecyclerView.ViewHolder(view) {

        val binding = MovieItemBinding.bind(view)

        fun bind(film: SearchDataModel) = with(binding){
            nameMovie.text = film.movieTitle
            releaseMovie.text = film.movieReleaseDate
            Picasso.get()
                .load("https://image.tmdb.org/t/p/original${film.movieCover}")
                .into(coverPlace)

            var overview = ""
            if (film.movieOverview.length > 160) {
                var counter = 0
                while (counter < 160) {
                    overview += film.movieOverview[counter]
                    counter++
                }
                overview += "..."
            } else overview = film.movieOverview
            overviewMovie.text = overview
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.movie_item, parent, false)
        return FilmHolder(view)
    }

    override fun onBindViewHolder(holder: FilmHolder, position: Int) {
        var pos = position
        holder.bind(filmList[position])
        holder.itemView.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                val activ = v!!.context as AppCompatActivity
                activ.supportActionBar?.setDisplayHomeAsUpEnabled(true)
                activ.supportActionBar?.subtitle = filmList[pos].movieTitle
                activ.supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.main_content_place, MoviePageFragment(filmList[pos].movieID))
                    .commit()
            }
        })
    }

    override fun getItemCount(): Int {
        return filmList.size
    }

    fun addMovie(film: SearchDataModel) {
        filmList.add(film)
        notifyDataSetChanged()
    }

    fun deleteData() {
        filmList = ArrayList<SearchDataModel>()
        notifyDataSetChanged()
    }
}
