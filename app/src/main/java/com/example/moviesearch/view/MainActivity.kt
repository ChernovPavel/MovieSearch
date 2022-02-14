package com.example.moviesearch.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.moviesearch.R
import com.example.moviesearch.databinding.MainActivityBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: MainActivityBinding
    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = MainActivityBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, ListFragment.newInstance())
                .commitNow()
        }

        bottomNavigationView = findViewById(R.id.main_bottom_navigation)

        bottomNavigationView.setOnItemSelectedListener { id ->
            val fragment: Fragment
            when (id.itemId) {
                R.id.bottom_menu_list_movies -> {
                    fragment = ListFragment()
                    loadFragment(fragment)
                    true
                }
                R.id.bottom_menu_favorites -> {
                    fragment = FavoritesMoviesFragment()
                    loadFragment(fragment)
                    true
                }
                R.id.bottom_menu_settings -> {
                    fragment = SettingsFragment()
                    loadFragment(fragment)
                    true
                }
                else -> false

            }
        }
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}