package com.example.moviesearch.view.details

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.moviesearch.R
import com.example.moviesearch.databinding.FragmentDetailsBinding
import com.example.moviesearch.model.Movie
import com.example.moviesearch.utils.showSnackBar
import com.example.moviesearch.viewmodel.AppState
import com.example.moviesearch.viewmodel.DetailsViewModel
import kotlinx.android.synthetic.main.fragment_details.*

class DetailsFragment : Fragment() {

    var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!
    private var movieId: Int = -1
    private val viewModel: DetailsViewModel by activityViewModels()

    companion object {
        const val BUNDLE_EXTRA = "movieId"
        fun newInstance(bundle: Bundle): DetailsFragment {
            val fragment = DetailsFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    private fun renderData(appState: AppState) {

        when (appState) {
            is AppState.Success -> {
                binding.detailsFragmentLoadingLayout.visibility = View.GONE
                binding.fragmentMovieDetails.visibility = View.VISIBLE
                setMovie(appState.movieData[0])
            }
            is AppState.Loading -> {
                binding.detailsFragmentLoadingLayout.visibility = View.VISIBLE
                binding.fragmentMovieDetails.visibility = View.GONE
            }
            is AppState.Error -> {
                binding.detailsFragmentLoadingLayout.visibility = View.GONE
                binding.fragmentMovieDetails.visibility = View.VISIBLE
                fragmentMovieDetails.showSnackBar(
                    getString(R.string.error),
                    getString(R.string.reload),
                    {
                        viewModel.getMovieFromAPI(movieId)
                    })
            }
        }
    }

    private fun setMovie(movie: Movie) {
        with(binding) {
            movieName.text = movie.title
            movieOverview.text = movie.overview
            movieGenre.text = movie.genre
            movieReleaseDate.text = movie.release_date
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        movieId = arguments?.getInt(BUNDLE_EXTRA) ?: -1

        viewModel.detailsLiveData.observe(viewLifecycleOwner, { renderData(it) })
        viewModel.getMovieFromAPI(movieId)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
