package com.alicea.discovermovie

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.alicea.discovermovie.databinding.ActivitySplashMovieBinding

class SplashMovie : AppCompatActivity() {

    private lateinit var binding: ActivitySplashMovieBinding
    private lateinit var mainHandler: Handler

    private val updateTextTask = Runnable {
        mainHandler.postDelayed({
            if (NetworkHelper.checkConnectivity(this@SplashMovie)) {
                val intent = Intent(this@SplashMovie, MainActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this@SplashMovie,"No internet connection", Toast.LENGTH_SHORT).show()
            }
        }, 3000)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mainHandler = Handler(Looper.getMainLooper())
    }
    override fun onPause() {
        super.onPause()
        mainHandler.removeCallbacks(updateTextTask)
    }

    override fun onResume() {
        super.onResume()
        mainHandler.post(updateTextTask)
    }
}