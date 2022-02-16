package com.example.moviesearch.view.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.moviesearch.databinding.FragmentListBinding
import com.example.moviesearch.model.Movie
import com.example.moviesearch.viewmodel.MainViewModel

class ListFragment : Fragment() {

    companion object {
        fun newInstance() = ListFragment()
    }

    private lateinit var viewModel: MainViewModel

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!
    private val adapter = ListFragmentAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.listFragmentRecyclerView.adapter = adapter
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        //назначаем наблюдателя, который при изменении даты будет обновлять список фильмов
        viewModel.getLiveData()
            .observe(viewLifecycleOwner, { adapter.setMovie(it as List<Movie>) })

        //запрашивам список фильмов из локального хранилища, чтобы liveData обновилась
        //и адаптер также обновил список фильмов в ресайклере
        viewModel.getMovieFromLocalSource()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}