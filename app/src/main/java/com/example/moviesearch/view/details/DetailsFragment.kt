package com.example.moviesearch.view.details

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.moviesearch.BuildConfig
import com.example.moviesearch.databinding.FragmentDetailsBinding
import com.example.moviesearch.model.Movie
import com.example.moviesearch.viewmodel.AppState
import com.example.moviesearch.viewmodel.DetailsViewModel
import kotlinx.android.synthetic.main.fragment_details.*

const val DETAILS_INTENT_FILTER = "DETAILS INTENT FILTER"
const val DETAILS_LOAD_RESULT_EXTRA = "LOAD RESULT"
const val DETAILS_INTENT_EMPTY_EXTRA = "INTENT IS EMPTY"
const val DETAILS_DATA_EMPTY_EXTRA = "DATA IS EMPTY"
const val DETAILS_RESPONSE_EMPTY_EXTRA = "RESPONSE IS EMPTY"
const val DETAILS_REQUEST_ERROR_EXTRA = "REQUEST ERROR"
const val DETAILS_REQUEST_ERROR_MESSAGE_EXTRA = "REQUEST ERROR MESSAGE"
const val DETAILS_URL_MALFORMED_EXTRA = "URL MALFORMED"
const val DETAILS_RESPONSE_SUCCESS_EXTRA = "RESPONSE SUCCESS"
const val DETAILS_EXTRA = "DETAILS_MOVIE_DTO"
private const val PROCESS_ERROR = "Обработка ошибки"
private const val MAIN_LINK = "https://api.themoviedb.org/3/movie/"

class DetailsFragment : Fragment() {

    var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!
    private var movieBundle: Int = -1
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

        detailsFragmentLoadingLayout.visibility = View.GONE
        fragmentMovieDetails.visibility = View.VISIBLE

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
                Toast.makeText(context, "Ошибка получения данных по фильму", Toast.LENGTH_SHORT).show()
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

        arguments?.getInt(BUNDLE_EXTRA)?.let { id ->
            movieBundle = id
        }

        viewModel.getLiveData().observe(viewLifecycleOwner, { renderData(it) })
        viewModel.getMovieFromRemoteSource(MAIN_LINK + movieBundle + "?api_key=${BuildConfig.MOVIE_API_KEY}&language=ru")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
