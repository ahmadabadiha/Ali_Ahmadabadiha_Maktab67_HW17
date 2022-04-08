package com.example.hw17

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.example.hw17.databinding.ActivityMainBinding
import com.example.hw17.uilayer.MovieListFragment
import com.example.hw17.uilayer.PlayVideoFragment
import com.example.hw17.uilayer.UpcomingFragment

class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bottomNavigationView = binding.bottomNavigation
        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.movieListFragment -> {
                    changeFragment(MovieListFragment())
                    true
                }
                R.id.upcomingFragment -> {
                    changeFragment(UpcomingFragment())
                    true
                }
                R.id.playVideoFragment -> {
                    changeFragment(PlayVideoFragment())
                    true
                }
                else -> false
            }

        }

    }

    private fun changeFragment(fragment: Fragment) {
        supportFragmentManager.commit {
            replace(R.id.fragmentContainerView, fragment)
            setReorderingAllowed(true)
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}