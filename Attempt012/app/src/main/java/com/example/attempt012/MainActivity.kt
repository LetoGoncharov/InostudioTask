package com.example.attempt012

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import com.example.attempt012.databinding.ActivityMainBinding
import com.example.attempt012.fragments.DiscoveryFragment
import com.example.attempt012.fragments.SearchFragment

class MainActivity : AppCompatActivity() {

    private val movie_page : CheckPageFragment by viewModels()

    lateinit var binding: ActivityMainBinding

    var is_need_to_turn_DisplayHome = true

    var isDiscovery = false
    var isSearch = false
    var wasDiscovery = false
    var wasSearch = false
    var isMoviePage = false
    var wasMoviePage = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        open_discovery()
        movie_page.isMoviePageOpened.observe(this, { res ->
            if (res) {
                is_need_to_turn_DisplayHome = true
                invalidateOptionsMenu()
                isMoviePage = true
                wasMoviePage = false
            } else {

            }
        })
        movie_page.wasDiscovery.observe(this, { res ->
            if (res) {
                wasDiscovery = true
                isDiscovery = false
                wasSearch = false
            }
        })
        movie_page.wasSearch.observe(this, { res ->
            if (res) {
                wasSearch = true
                isSearch = false
                wasDiscovery = false
            }
        })
    }

    fun open_discovery() {
        supportActionBar?.subtitle = "Коллекция"
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.main_content_place, DiscoveryFragment.newInstance())
            .commit()
    }

    fun open_search() {
        supportActionBar?.subtitle = "поиск"
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.main_content_place, SearchFragment.newInstance())
            .commit()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        if (is_need_to_turn_DisplayHome) menuInflater.inflate(R.menu.search_button_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            android.R.id.home -> {
                if (isSearch) {
                    open_discovery()
                    supportActionBar?.setDisplayHomeAsUpEnabled(false)
                    is_need_to_turn_DisplayHome = true
                    invalidateOptionsMenu()
                    isDiscovery = true
                    isSearch = false
                    isMoviePage = false
                    wasSearch = true
                    wasDiscovery = false
                    wasMoviePage = false
                } else if (isMoviePage && wasSearch) {
                    open_search()
                    supportActionBar?.setDisplayHomeAsUpEnabled(true)
                    is_need_to_turn_DisplayHome = false
                    invalidateOptionsMenu()
                    isMoviePage = false
                    isSearch = true
                    isDiscovery = false
                    wasMoviePage = true
                    wasDiscovery = false
                    wasSearch = false
                } else if (isMoviePage && wasDiscovery) {
                    open_discovery()
                    supportActionBar?.setDisplayHomeAsUpEnabled(false)
                    is_need_to_turn_DisplayHome = true
                    invalidateOptionsMenu()
                    isDiscovery = true
                    isSearch = false
                    isMoviePage = false
                    wasSearch = false
                    wasDiscovery = false
                    wasMoviePage = true
                } else {

                }
            }
            R.id.search_button -> {
                open_search()
                supportActionBar?.setDisplayHomeAsUpEnabled(true)
                is_need_to_turn_DisplayHome = false
                invalidateOptionsMenu()
                isSearch = true

            }
        }


        return super.onOptionsItemSelected(item)
    }
}