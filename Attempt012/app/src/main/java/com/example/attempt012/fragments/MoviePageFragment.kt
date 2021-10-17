package com.example.attempt012.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.attempt012.CheckPageFragment
import com.example.attempt012.R
import com.example.attempt012.databinding.FragmentMoviePageBinding
import com.example.attempt012.moviepage.crew.builder.CrewBuilder
import com.example.attempt012.moviepage.crew.drawing.adapter.CrewAdapter
import com.example.attempt012.moviepage.crew.drawing.model.CrewModel
import com.example.attempt012.moviepage.crew.model.CrewData
import com.example.attempt012.moviepage.getails.builder.DetailBuilder
import com.example.attempt012.moviepage.getails.model.DataDetails
import com.example.attempt012.moviepage.photos.builder.PhotoBuilder
import com.example.attempt012.moviepage.photos.drawing.adapter.PhotoAdapter
import com.example.attempt012.moviepage.photos.drawing.adapter.PosterAdapter
import com.example.attempt012.moviepage.photos.drawing.model.PhotoModel
import com.example.attempt012.moviepage.photos.drawing.model.PosterModel
import com.example.attempt012.moviepage.photos.model.PhotoData
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MoviePageFragment(movie_id: Int): Fragment() {

    lateinit var binding: FragmentMoviePageBinding
    lateinit var top_progress_bar : ProgressBar
    lateinit var lay_manager : GridLayoutManager

    private val crewAdapter = CrewAdapter()
    private val photoAdapter = PhotoAdapter()
    private val posterAdapter = PosterAdapter()
    private val actorAdapter = CrewAdapter()
    private val opened : CheckPageFragment by activityViewModels()

    var data_request = DetailBuilder()
    var crew_data_request = CrewBuilder()
    var photo_data_request = PhotoBuilder()

    var temporary_id = movie_id

    var isStarFull = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMoviePageBinding.inflate(inflater)
        opened.isMoviePageOpened.value = true


        top_progress_bar = binding.topProgressBarHeader
        top_progress_bar.visibility = View.VISIBLE

        lay_manager = GridLayoutManager(context, 1)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        opened.isMoviePageOpened.value = false
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        start_proc()
    }

    fun start_proc() {
        call_everything()
        binding.favouriteButton.setOnClickListener{ btn ->
            if (!isStarFull) {
                btn.setBackgroundResource(R.drawable.ic_full_star)
                isStarFull = true
            } else {
                btn.setBackgroundResource(R.drawable.ic_empty_star)
                isStarFull = false
            }
        }
    }

    fun call_everything() {
        call_movie_data()
        call_crew_data()
        call_photo_data()
    }

    fun call_movie_data() {
        top_progress_bar.visibility = View.VISIBLE

        var current_data = data_request.call_data(temporary_id)

        current_data.enqueue(object : Callback<DataDetails?> {
            override fun onResponse(call: Call<DataDetails?>, response: Response<DataDetails?>) {
                val res = response.body()!!

                if(response.isSuccessful) {
                    binding.movieNameHeader.text = res.title
                } else {
                    Log.d("Response", response.errorBody().toString())
                }

                binding.movieNameHeader.text = res.title
                binding.taglineHeader.text = "      " + res.tagline
                binding.movieBudget.text = res.budget.toString() + " $"
                binding.originalTitle.text = res.original_title
                binding.releaseDateH.text = res.release_date
                binding.movieRating.text = res.vote_average.toString()
                binding.movieOverview.text = "      " + res.overview
                Picasso.get()
                    .load("https://image.tmdb.org/t/p/original${res.poster_path}")
                    .into(binding.moviePosterHeader)
                top_progress_bar.visibility = View.GONE
            }

            override fun onFailure(call: Call<DataDetails?>, t: Throwable) {
                Toast.makeText(context, "Lol", Toast.LENGTH_SHORT).show()
                Log.d("Response", "check here ----- "+call.request().toString())
                Log.d("Response", "check here ----- "+call.request().url)
                binding.alertMessages.visibility = View.VISIBLE
                binding.updateButtonMoviePage.visibility = View.VISIBLE
                top_progress_bar.visibility = View.GONE
                binding.alertMessages.text = "Не удалось получить всю информацию о фильме\nпопробуйте еще"
                binding.updateButtonMoviePage.setOnClickListener{
                    Log.d("Response", "check here ----- "+call.request().url)
                    binding.alertMessages.visibility = View.GONE
                    binding.updateButtonMoviePage.visibility = View.GONE
                    call_everything()
                }

            }
        })
    }

    fun call_crew_data() {
        top_progress_bar.visibility = View.VISIBLE
        var current_data = crew_data_request.call_data(temporary_id)

        current_data.enqueue(object : Callback<CrewData?> {
            override fun onResponse(call: Call<CrewData?>, response: Response<CrewData?>) {
                val res = response.body()!!
                res.cast.forEach { crew ->

                    Log.d("Response", "Here's photo -->"+crew.profile_path)
                    Log.d("Response", "here's name --->" + crew.name)
                    Log.d("Response", "here's role --->" +  crew.known_for_department)
                    var name = ""
                    var role = ""
                    var profile = ""
                    if (crew.name.isNullOrBlank()) {
                        name = "Ошибка"
                    } else name = crew.name
                    if (crew.known_for_department.isNullOrBlank()) {
                        role = "Ошибка"
                    } else role = crew.known_for_department
                    if (crew.profile_path.isNullOrBlank()) {
                        profile = "---"
                    } else profile = crew.profile_path

                    draw_actor(role, profile, name)

                }
                res.crew.forEach { crew ->

                    Log.d("Response", "Here's photo -->"+crew.profile_path)
                    Log.d("Response", "here's name --->" + crew.name)
                    Log.d("Response", "here's role --->" +  crew.job)
                    var name = ""
                    var role = ""
                    var profile = ""
                    if (crew.name.isNullOrBlank()) {
                        name = "Ошибка"
                    } else name = crew.name
                    if (crew.job.isNullOrBlank()) {
                        role = "Ошибка"
                    } else role = crew.job
                    if (crew.profile_path.isNullOrBlank()) {
                        profile = "---"
                    } else profile = crew.profile_path
                    draw_crewmate(role, profile, name)

                }

                if (res.cast.size == 0) {
                    binding.actorsHolder.visibility = View.GONE
                } else binding.actorsHolder.visibility = View.VISIBLE
                if (res.crew.size == 0) {
                    binding.crewHolder.visibility = View.GONE
                } else binding.crewHolder.visibility = View.VISIBLE
                top_progress_bar.visibility = View.GONE

            }

            override fun onFailure(call: Call<CrewData?>, t: Throwable) {
                Toast.makeText(context, "Data of crew hasn't been gotten", Toast.LENGTH_SHORT).show()
                binding.alertMessages.visibility = View.VISIBLE
                binding.updateButtonMoviePage.visibility = View.VISIBLE
                top_progress_bar.visibility = View.GONE
                binding.alertMessages.text = "Не удалось получить всю информацию о фильме\nпопробуйте еще"
                binding.updateButtonMoviePage.setOnClickListener{
                    Log.d("Response", "check here ----- "+call.request().url)
                    binding.alertMessages.visibility = View.GONE
                    binding.updateButtonMoviePage.visibility = View.GONE
                    call_everything()
                }
            }
        })
    }

    fun draw_crewmate(role: String, photo: String, name: String) {
        binding.apply {
//            actorsRecycler.layoutManager = lay_manager
            crewRecycler.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            crewRecycler.adapter = crewAdapter

            val crewmate = CrewModel(role, photo, name)
            crewAdapter.addCrewmate(crewmate)
        }
    }
    fun draw_actor(role: String, photo: String, name: String) {
        binding.apply {
//            actorsRecycler.layoutManager = lay_manager
            actorsRecycler.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            actorsRecycler.adapter = actorAdapter

            val crewmate = CrewModel(role, photo, name)
            actorAdapter.addCrewmate(crewmate)
        }
    }

    fun call_photo_data() {
        top_progress_bar.visibility = View.VISIBLE
        var current_data = photo_data_request.call_data(temporary_id)
        current_data.enqueue(object : Callback<PhotoData?> {
            override fun onResponse(call: Call<PhotoData?>, response: Response<PhotoData?>) {
                var rsl = response.body()!!

                rsl.posters.forEach { poster ->
                    var pic = ""
                    if (poster.file_path.isNullOrBlank()) {
                        pic = "---"
                    } else pic = poster.file_path
                    draw_poster(pic)
                }
                rsl.backdrops.forEach { backdr ->
                    var pic = ""
                    if (backdr.file_path.isNullOrBlank()) {
                        pic = "---"
                    } else pic = backdr.file_path
                    draw_photo(pic)
                }
                if (rsl.posters.size == 0) {
                    binding.postersLayout.visibility = View.GONE
                } else binding.postersLayout.visibility = View.VISIBLE
                if (rsl.backdrops.size == 0) {
                    binding.photosLayout.visibility = View.GONE
                } else binding.photosLayout.visibility = View.VISIBLE
                top_progress_bar.visibility = View.GONE

            }

            override fun onFailure(call: Call<PhotoData?>, t: Throwable) {
                Toast.makeText(context, "Photos haven't been gotten", Toast.LENGTH_SHORT)
                binding.alertMessages.visibility = View.VISIBLE
                binding.updateButtonMoviePage.visibility = View.VISIBLE
                top_progress_bar.visibility = View.GONE
                binding.alertMessages.text = "Не удалось получить всю информацию о фильме\nпопробуйте еще"
                binding.updateButtonMoviePage.setOnClickListener{
                    Log.d("Response", "check here ----- "+call.request().url)
                    binding.alertMessages.visibility = View.GONE
                    binding.updateButtonMoviePage.visibility = View.GONE
                    call_everything()
                }
            }
        })
    }

    fun draw_photo(picture: String) {
        binding.apply {
            photoRecycler.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            photoRecycler.adapter = photoAdapter

            val pic = PhotoModel(picture)
            photoAdapter.addPhoto(pic)
        }
    }
    fun draw_poster(picture: String) {
        binding.apply {
            posterRecycler.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            posterRecycler.adapter = posterAdapter

            val pic = PosterModel(picture)
            posterAdapter.addPoster(pic)
        }
    }



    companion object {
        fun newInstance(movie_id: Int) = MoviePageFragment(movie_id)
    }
}