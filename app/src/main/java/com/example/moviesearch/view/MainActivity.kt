package com.example.moviesearch.view

import android.content.IntentFilter
import android.net.ConnectivityManager.CONNECTIVITY_ACTION
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.moviesearch.MainBroadcastReceiver
import com.example.moviesearch.R
import com.example.moviesearch.databinding.MainActivityBinding
import com.example.moviesearch.view.contacts.ContactsFragment
import com.example.moviesearch.view.favorites.FavoritesMoviesFragment
import com.example.moviesearch.view.history.HistoryFragment
import com.example.moviesearch.view.main.ListFragment
import com.example.moviesearch.view.map.MapsFragment
import com.example.moviesearch.view.settings.SettingsFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: MainActivityBinding
    private var receiver = MainBroadcastReceiver()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registerReceiver(receiver, IntentFilter(CONNECTIVITY_ACTION))

        binding = MainActivityBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        savedInstanceState?.let {} ?: run {
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
                R.id.bottom_menu_favorites -> {
                    loadFragment(FavoritesMoviesFragment.newInstance())
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_history -> {
                if (supportFragmentManager.findFragmentByTag("history") == null) {
                    supportFragmentManager.apply {
                        beginTransaction()
                            .add(R.id.fragment_container, HistoryFragment.newInstance(), "history")
                            .addToBackStack("")
                            .commitAllowingStateLoss()
                    }
                }
                true
            }
            R.id.menu_contacts -> {
                if (supportFragmentManager.findFragmentByTag("contacts") == null) {
                    supportFragmentManager.apply {
                        beginTransaction()
                            .add(R.id.fragment_container,
                                ContactsFragment.newInstance(),
                                "contacts")
                            .addToBackStack("")
                            .commitAllowingStateLoss()
                    }
                }
                true
            }
            R.id.menu_google_maps -> {
                if (supportFragmentManager.findFragmentByTag("maps") == null) {
                    supportFragmentManager.apply {
                        beginTransaction()
                            .add(R.id.fragment_container, MapsFragment.newInstance(), "maps")
                            .addToBackStack("")
                            .commitAllowingStateLoss()
                    }
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}