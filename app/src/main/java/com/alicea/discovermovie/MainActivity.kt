package com.alicea.discovermovie

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.GridLayoutManager
import com.alicea.discovermovie.adapter.MovieAdapter
import com.alicea.discovermovie.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var movieAdapter: MovieAdapter
    private var handler: Handler = Handler()
    private var runnable: Runnable? = null
    private var delay = 60000
    private val broadcastReceiver: BroadcastReceiver = object : BroadcastReceiver() {
       override fun onReceive(context: Context, intent: Intent) {
            val message = intent.getStringExtra("message")
            Toast.makeText(this@MainActivity,message, Toast.LENGTH_SHORT).show()
       }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initRecyclerView()
        viewModel = obtainViewModel(this@MainActivity)
        viewModel.getAllMovies().observe(this) { movieList ->
            if (movieList.isNotEmpty()) {
                if (binding.btnUpdate.visibility == View.GONE) {
                    movieAdapter.setMovieList(movieList)
                } else {
                    binding.btnUpdate.setOnClickListener {
                        movieAdapter.setMovieList(movieList)
                        binding.btnUpdate.visibility = View.GONE
                        val intent = Intent("custom-action-local-broadcast")
                        intent.putExtra("message", "Tampilan diperbarui")
                        LocalBroadcastManager.getInstance(this@MainActivity).sendBroadcast(intent)
                    }
                }
            } else {
                viewModel.getPopularMovies()
            }
        }

        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver, IntentFilter("custom-action-local-broadcast"))
    }
    private fun initRecyclerView() {
        movieAdapter = MovieAdapter()
        binding.rvMovies.apply {
            layoutManager = GridLayoutManager(applicationContext, 1)
            adapter = movieAdapter
        }
    }
    override fun onPause() {
        super.onPause()
        handler.removeCallbacks(runnable!!)
    }
    override fun onResume() {
        super.onResume()
        handler.postDelayed(Runnable {
            handler.postDelayed(runnable!!, delay.toLong())
            if (NetworkHelper.checkConnectivity(this@MainActivity)){
                viewModel.getPopularMovies()
                binding.btnUpdate.visibility = View.VISIBLE
            }
        }.also { runnable = it }, delay.toLong())
    }
    private fun obtainViewModel(activity: AppCompatActivity): MainViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[MainViewModel::class.java]
    }
}