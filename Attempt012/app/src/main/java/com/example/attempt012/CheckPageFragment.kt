package com.example.attempt012

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class CheckPageFragment : ViewModel() {
    val isMoviePageOpened : MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    val wasDiscovery : MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    val wasSearch : MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }
}