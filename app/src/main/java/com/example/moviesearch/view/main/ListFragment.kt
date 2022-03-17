package com.example.moviesearch.view.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.example.moviesearch.R
import com.example.moviesearch.databinding.FragmentListBinding
import com.example.moviesearch.model.Movie
import com.example.moviesearch.utils.showSnackBar
import com.example.moviesearch.view.details.DetailsFragment
import com.example.moviesearch.viewmodel.AppState
import com.example.moviesearch.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.fragment_list.*

class ListFragment : Fragment() {

    companion object {
        fun newInstance() = ListFragment()
    }

    // запрашиваем ViewModel активити. Чтобы на несколько фрагментов создавалась одна ViewModel
    private val viewModel: MainViewModel by activityViewModels()

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    private val adapter = ListFragmentAdapter(object : OnItemViewClickListener {
        override fun onItemViewClick(movie: Movie) {

            activity?.supportFragmentManager?.apply {
                val bundle = Bundle()
                bundle.putInt(DetailsFragment.BUNDLE_EXTRA, movie.id)
                beginTransaction()
                    .add(R.id.fragment_container, DetailsFragment.newInstance(bundle))
                    .addToBackStack("")
                    .commitAllowingStateLoss()
            }
        }
    })


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.listFragmentRecyclerView.adapter = adapter

        //назначаем наблюдателя, который при изменении даты будет обновлять список фильмов
        viewModel.liveListMoviesToObserve.observe(viewLifecycleOwner, {renderData(it)})

        //запрашивам список фильмов из локального хранилища, чтобы liveData обновилась
        //и адаптер также обновил список фильмов в ресайклере
        viewModel.getListTopMoviesFromAPI()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDestroy() {
        adapter.removeListener()
        super.onDestroy()
    }

    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Success -> {
                val movieData = appState.movieData
                listFragmentLoadingLayout.visibility = View.GONE
                adapter.setMovie(movieData)
            }
            is AppState.Loading -> {
                listFragmentLoadingLayout.visibility = View.VISIBLE
            }
            is AppState.Error -> {
                listFragmentLoadingLayout.visibility = View.GONE
                fragmentListRootView.showSnackBar(
                    getString(R.string.error),
                    getString(R.string.reload),
                    { viewModel.getListTopMoviesFromAPI() }
                )
            }
        }
    }

    /**
     * интерфейс клика по айтему.
     * Вызывается onItemViewClick в методе bind() холдера
     * А реализуется метод при создании адаптера (логика что делать по нажатию на элемент списка)
     */
    interface OnItemViewClickListener {
        fun onItemViewClick(movie: Movie)
    }
}