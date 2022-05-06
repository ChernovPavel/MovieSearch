package com.example.moviesearch.view.main

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DiffUtil
import com.example.moviesearch.R
import com.example.moviesearch.app.App
import com.example.moviesearch.databinding.FragmentListBinding
import com.example.moviesearch.model.Movie
import com.example.moviesearch.utils.IS_RUSSIAN_LANGUAGE
import com.example.moviesearch.utils.showSnackBar
import com.example.moviesearch.view.details.DetailsFragment
import com.example.moviesearch.viewmodel.AppState
import com.example.moviesearch.viewmodel.ListViewModel
import com.example.moviesearch.viewmodel.ListViewModelFactory
import javax.inject.Inject

class ListFragment : Fragment() {

    companion object {
        fun newInstance() = ListFragment()
    }

    @Inject
    lateinit var lvmFactory: ListViewModelFactory

    private val viewModel: ListViewModel by viewModels { lvmFactory }

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    private val adapter = ListFragmentAdapter(object : OnItemViewClickListener {
        override fun onItemViewClick(movie: Movie) {

            activity?.supportFragmentManager?.apply {
                beginTransaction()
                    .add(R.id.fragment_container, DetailsFragment.newInstance(movie.id))
                    .addToBackStack("")
                    .commit()
            }
        }
    })

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        (requireActivity().applicationContext as App).appComponent.inject(this)
        _binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.listFragmentRecyclerView.adapter = adapter
        viewModel.liveListMoviesToObserve.observe(viewLifecycleOwner) { renderData(it) }
        showMoviesList()
    }

    private fun showMoviesList() {
        activity?.let {
            viewModel.getListTopMoviesFromAPI(
                it.getPreferences(Context.MODE_PRIVATE)
                    .getBoolean(IS_RUSSIAN_LANGUAGE, false)
            )
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    override fun onDestroy() {
        adapter.removeListener()
        super.onDestroy()
    }

    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Success -> {
                val movieData = appState.movieData
                binding.includedLoadingLayout.loadingLayout.visibility = View.GONE
                val movieDiffUtilCallback = MoviesDiffUtilsCallback(adapter.movieData, movieData)
                val movieDiffResult = DiffUtil.calculateDiff(movieDiffUtilCallback)
                adapter.setMovie(movieData)
                movieDiffResult.dispatchUpdatesTo(adapter)
            }
            is AppState.Loading -> {
                binding.includedLoadingLayout.loadingLayout.visibility = View.VISIBLE
            }
            is AppState.Error -> {
                binding.includedLoadingLayout.loadingLayout.visibility = View.GONE
                binding.fragmentListRootView.showSnackBar(
                    appState.error.message.toString(),
                    getString(R.string.reload),
                    { showMoviesList() }
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