package com.alicea.discovermovie

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.alicea.discovermovie.database.SavedMovie

class SplashViewModel(application: Application): ViewModel() {
    private val movieRepository: MovieRepository = MovieRepository(application)
    fun getAllMovies(): LiveData<List<SavedMovie>> = movieRepository.getAllMovies()

}