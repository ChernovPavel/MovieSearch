package com.example.moviesearch.view.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.moviesearch.databinding.FragmentFavoritesMoviesBinding
import com.example.moviesearch.viewmodel.MainViewModel


class FavoritesMoviesFragment : Fragment() {

    companion object {
        fun newInstance() = FavoritesMoviesFragment()
    }

    private lateinit var viewModel: MainViewModel
    var _binding: FragmentFavoritesMoviesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFavoritesMoviesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}