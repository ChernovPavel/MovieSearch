package com.example.moviesearch.view.main

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.moviesearch.R
import com.example.moviesearch.databinding.FragmentListBinding
import com.example.moviesearch.model.Movie
import com.example.moviesearch.utils.showSnackBar
import com.example.moviesearch.view.details.DetailsFragment
import com.example.moviesearch.view.settings.IS_RUSSIAN_LANGUAGE
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
        viewModel.liveListMoviesToObserve.observe(viewLifecycleOwner, { renderData(it) })
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
                binding.includedLoadingLayout.loadingLayout.visibility = View.GONE
                adapter.setMovie(movieData)
            }
            is AppState.Loading -> {
                binding.includedLoadingLayout.loadingLayout.visibility = View.VISIBLE
            }
            is AppState.Error -> {
                binding.includedLoadingLayout.loadingLayout.visibility = View.GONE
                fragmentListRootView.showSnackBar(
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