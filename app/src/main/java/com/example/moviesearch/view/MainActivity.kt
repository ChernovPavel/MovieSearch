package com.example.moviesearch.view

import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.moviesearch.MainBroadcastReceiver
import com.example.moviesearch.R
import com.example.moviesearch.databinding.MainActivityBinding
import com.example.moviesearch.view.history.HistoryFragment
import com.example.moviesearch.view.main.ListFragment
import com.example.moviesearch.view.settings.SettingsFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: MainActivityBinding
    private var receiver = MainBroadcastReceiver()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registerReceiver(receiver, IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED))

        binding = MainActivityBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, ListFragment.newInstance())
                .commit()
        }

        binding.mainBottomNavigation.setOnItemSelectedListener { id ->
            when (id.itemId) {
                R.id.bottom_menu_list_movies -> {
                    loadFragment(ListFragment.newInstance())
                    true
                }
                R.id.bottom_menu_history -> {
                    loadFragment(HistoryFragment.newInstance())
                    true
                }
                R.id.bottom_menu_settings -> {
                    loadFragment(SettingsFragment.newInstance())
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
        supportFragmentManager.popBackStack()
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(receiver)
    }
}