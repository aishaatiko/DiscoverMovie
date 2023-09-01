package com.alicea.discovermovie.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface MovieApi {
    @GET("discover/movie")
    fun getPopularMovies(@Header("Authorization") authHeader: String?) : Call<ResultMovies>
}