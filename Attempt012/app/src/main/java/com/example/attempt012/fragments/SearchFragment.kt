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
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.attempt012.CheckPageFragment
import com.example.attempt012.R
import com.example.attempt012.databinding.FragmentDiscoveryBinding
import com.example.attempt012.databinding.FragmentSearchBinding
import com.example.attempt012.discovery.drawing.model.DiscoveryDataModel
import com.example.attempt012.discovery.model.DiscoveryResults
import com.example.attempt012.search.builder.SearchBuilder
import com.example.attempt012.search.drawing.adapter.SearchAdapter
import com.example.attempt012.search.drawing.model.SearchDataModel
import com.example.attempt012.search.model.SearchModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchFragment : Fragment() {

    lateinit var binding: FragmentSearchBinding
    lateinit var top_progress_bar : ProgressBar
    lateinit var bottom_progress_bar : ProgressBar
    lateinit var lay_manager : GridLayoutManager

    private val movieAdapter = SearchAdapter()
    private val opened : CheckPageFragment by activityViewModels()

    var data_request = SearchBuilder()
    var count_cycles = 0
    var current_pages = 1
    var total_pages_of_list = 500
    var current_query = "Дюна"
    var result_of_response = false

    // for scroll
    var pastVisibleItems = 0
    var visibleItemCount = 0
    var totalItemCount = 0
    var isLoading = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSearchBinding.inflate(inflater)

        opened.wasSearch.value = false

        top_progress_bar = binding.TopProgressBar
        bottom_progress_bar = binding.BottomProgressBar
        top_progress_bar.visibility = View.VISIBLE
        bottom_progress_bar.visibility = View.GONE

        lay_manager = GridLayoutManager(context, 1)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        call_data(current_query, current_pages)
        binding.searchMovieText.setText("Пример поиска: '$current_query'")
        scroll()
        binding.startSearchButton.setOnClickListener{
            start_search()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        opened.wasSearch.value = true
    }

    fun start_search() {
        count_cycles = 0
        current_pages = 1
        movieAdapter.deleteData()
        current_query = binding.searchMovieText.text.toString()
        Log.d("Check", "here is your query ---->" + current_query)
        if (current_query != "") {
            binding.TopProgressBar.visibility = View.VISIBLE
            call_data(current_query, current_pages)

        } else {
            var duration = Toast.LENGTH_SHORT
            Toast.makeText(context, "Введите текст", duration).show()
        }
        binding.searchMovieText.setText("")
    }

    fun scroll() {
        binding.discoveryRecycleView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)


                visibleItemCount = lay_manager.childCount
                totalItemCount = lay_manager.itemCount
                pastVisibleItems = lay_manager.findFirstVisibleItemPosition()
                Log.d("Check", "totalItemCount --->" + totalItemCount)
                if (!isLoading && (current_pages < total_pages_of_list)) {

                    if(visibleItemCount + pastVisibleItems == totalItemCount) {
                        bottom_progress_bar.visibility = View.VISIBLE
                        isLoading = true
                        current_pages++
                        call_data(current_query, current_pages)
                    }
                } else if (!isLoading && (current_pages >= total_pages_of_list)) {
                    if(visibleItemCount + pastVisibleItems == totalItemCount) {
                        Toast.makeText(context, "Все фильмы загружены", Toast.LENGTH_SHORT).show()
                        isLoading = true
                    }
                }
            }
        })
    }


    fun call_data(query: String, page: Int) {

        var current_data = data_request.call_data(query, page)


        current_data.enqueue(object : Callback<SearchModel?> {
            override fun onResponse(call: Call<SearchModel?>, response: Response<SearchModel?>) {
                val resul = response.body()!!

                Log.d("Response", "Here are results of downloading ------>     "
                        + resul.page + "     "
                        + resul.total_pages +   "        "
                        + resul.total_results
                )
                total_pages_of_list = resul.total_pages
                if (resul.total_results != 0) {
                    resul.results.forEach { movie ->
                        var title = ""
                        var release = ""
                        var overview = ""
                        var poster = ""
                        if (movie.title.isNullOrBlank()) {
                            title = "Ошибка"
                        } else title = movie.title
                        if (movie.release_date.isNullOrBlank()) {
                            release = "Нет даты"
                        } else release = movie.release_date
                        if (movie.overview.isNullOrBlank()) {
                            overview = "     Рецензия не переведена или отсутствует"
                        } else overview = movie.overview
                        if (movie.poster_path.isNullOrBlank()) {
                            poster = "---"
                        } else poster = movie.poster_path

                        isLoading = false
                        draw_movie(movie.id, title, release, overview, poster)
                    }

                    binding.resultsOfSearch.text = count_cycles.toString() + "/" + resul.total_results
                } else {
                    binding.resultsOfSearch.text = "Не удалось ничего найти по Вашему запросу"
                }


                top_progress_bar.visibility = View.GONE
                bottom_progress_bar.visibility = View.GONE
                binding.discoveryRecycleView.scrollToPosition(pastVisibleItems)

            }

            override fun onFailure(call: Call<SearchModel?>, t: Throwable) {
                binding.resultsOfSearch.setPadding(20,50,20,50)
                binding.resultsOfSearch.text = "Не удалось подгрузить фильмы\nПроверьте интернет-соединение"
                top_progress_bar.visibility = View.GONE
                bottom_progress_bar.visibility = View.GONE
                binding.updateButtonSearch.visibility = View.VISIBLE
                binding.updateButtonSearch.setOnClickListener{
                    binding.updateButtonSearch.visibility = View.GONE
                    binding.resultsOfSearch.setPadding(5,10,5,0)
                    call_data(current_query, current_pages)
                }
            }
        })
    }

    fun draw_movie(id: Int, title: String, rel_date: String, over_view: String, cover: String) {
        count_cycles++
        binding.apply{
            discoveryRecycleView.layoutManager = lay_manager
//            discoveryRecycleView.layoutManager = GridLayoutManager(this@MainActivity, 1) // Не терять строку, на всякий случай
            discoveryRecycleView.adapter = movieAdapter

            val film = SearchDataModel(id, title, rel_date, over_view, cover)
            movieAdapter.addMovie(film)
            Log.d("Check", "This is total_pages ---->" + total_pages_of_list)
        }
    }

    companion object {
        fun newInstance() = SearchFragment()
    }
}