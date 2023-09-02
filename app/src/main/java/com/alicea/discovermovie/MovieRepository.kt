package com.alicea.discovermovie

import android.app.Application
import androidx.lifecycle.LiveData
import com.alicea.discovermovie.database.Movie
import com.alicea.discovermovie.database.MovieDao
import com.alicea.discovermovie.database.MovieRoomDatabase
import com.alicea.discovermovie.database.SavedMovie
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class MovieRepository(application: Application) {
    private val movieDao: MovieDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()
    init {
        val db = MovieRoomDatabase.getDatabase(application)
        movieDao = db.movieDao()
    }
    fun getAllMovies(): LiveData<List<SavedMovie>> = movieDao.getAllMovies()
    fun insert(movie: List<SavedMovie>) {
        executorService.execute { movieDao.insert(movie) }
    }
    fun delete() {
        executorService.execute { movieDao.delete() }
    }
    fun update(movie: List<SavedMovie>) {
        executorService.execute { movieDao.update(movie) }
    }
}