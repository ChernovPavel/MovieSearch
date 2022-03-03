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
import com.example.moviesearch.model.MovieDTO
import com.example.moviesearch.viewmodel.MainViewModel
import com.google.android.material.snackbar.Snackbar
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
const val DETAILS_TITLE_EXTRA = "TITLE"
const val DETAILS_OVERVIEW_EXTRA = "OVERVIEW"
const val DETAILS_RELEASE_DATE_EXTRA= "RELEASE_DATE"
const val DETAILS_GENRES_EXTRA= "GENRES"
private const val PROCESS_ERROR = "Обработка ошибки"

class DetailsFragment : Fragment() {

    var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    // запрашиваем ViewModel активити. Чтобы на несколько фрагментов создавалась одна ViewModel
    private val viewModel: MainViewModel by activityViewModels()

    //реализация интерфейса объявленного в классе MovieLoader
    private val onLoadListener: MovieLoader.MovieLoaderListener =
        object : MovieLoader.MovieLoaderListener {
            override fun onLoaded(movieDTO: MovieDTO) {
                displayMovie(movieDTO)
            }

            override fun onFailed(throwable: Throwable) {
                Snackbar.make(
                    fragmentMovieDetails,
                    getString(R.string.network_error),
                    Snackbar.LENGTH_LONG
                )
                    .show()
                parentFragmentManager.popBackStack()
            }
        }

    private fun displayMovie(movieDTO: MovieDTO) {
        with(binding, {
            detailsFragmentLoadingLayout.visibility = View.GONE
            fragmentMovieDetails.visibility = View.VISIBLE

            movieName.text = movieDTO.title
            movieOverview.text = movieDTO.overview
            movieGenre.text = movieDTO.genres?.get(0)?.name
            movieReleaseDate.text = movieDTO.release_date
        })
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

        viewModel.selectedItem.observe(viewLifecycleOwner, {
            val movieBundle = it

            binding.detailsFragmentLoadingLayout.visibility = View.VISIBLE
            binding.fragmentMovieDetails.visibility = View.GONE

            val loader = MovieLoader(onLoadListener, movieBundle.id)
            loader.loadMovie()
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
