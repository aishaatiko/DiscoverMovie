package com.alicea.discovermovie

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.alicea.discovermovie.databinding.ActivitySplashMovieBinding

class SplashMovie : AppCompatActivity() {

    private lateinit var binding: ActivitySplashMovieBinding
    private lateinit var mainHandler: Handler
    private lateinit var viewModel: SplashViewModel

    private val updateTextTask = Runnable {
        viewModel.getAllMovies().observe(this) { movieList ->
            mainHandler.postDelayed({
                if (NetworkHelper.checkConnectivity(this@SplashMovie) || movieList.isNotEmpty()) {
                    val intent = Intent(this@SplashMovie, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this@SplashMovie,"No internet connection", Toast.LENGTH_SHORT).show()
                    if (movieList.isEmpty())
                        Toast.makeText(this@SplashMovie,"Local database not found", Toast.LENGTH_SHORT).show()
                }
            }, 3000)
        }

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mainHandler = Handler(Looper.getMainLooper())
        viewModel = obtainViewModel(this@SplashMovie)

    }
    override fun onPause() {
        super.onPause()
        mainHandler.removeCallbacks(updateTextTask)
    }

    override fun onResume() {
        super.onResume()
        mainHandler.post(updateTextTask)
    }
    private fun obtainViewModel(activity: AppCompatActivity): SplashViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[SplashViewModel::class.java]
    }
}