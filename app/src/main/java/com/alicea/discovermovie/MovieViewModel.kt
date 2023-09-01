package com.alicea.discovermovie

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alicea.discovermovie.database.Movie
import com.alicea.discovermovie.network.ResultMovies
import com.alicea.discovermovie.network.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {
    private var movieLiveData = MutableLiveData<List<Movie>>()
    private val token = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIzNjkyOTg4NzhkNWVhODI0NjUzODgxNzU2NjM4NjNlNiIsInN1YiI6IjVjNDY4NjA2MGUwYTI2NDk0ZGM4ODQ2OSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.EchfjPLbZS8Ff8GKw6vk92DybkNGIUQqywPUGpaIKCg"

    fun getPopularMovies() {
        RetrofitInstance.api.getPopularMovies("Bearer $token")
            .enqueue(object : Callback<ResultMovies> {
                override fun onResponse(call: Call<ResultMovies>, response: Response<ResultMovies>) {
                    if (response.body()!=null) {
                        movieLiveData.value = response.body()!!.results
                    } else {
                        return
                    }
                }

                override fun onFailure(call: Call<ResultMovies>, t: Throwable) {
                    Log.d("TAG", t.message.toString())
                }

            })
    }

    fun observeMovieLiveData() : LiveData<List<Movie>> {
        return movieLiveData
    }
}