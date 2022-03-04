package com.example.moviesearch.view.details

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.moviesearch.databinding.FragmentDetailsBinding
import com.example.moviesearch.model.Movie
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
const val DETAILS_EXTRA = "DETAILS_MOVIE_DTO"
private const val PROCESS_ERROR = "Обработка ошибки"

class DetailsFragment : Fragment() {

    var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    // запрашиваем ViewModel активити. Чтобы на несколько фрагментов создавалась одна ViewModel
    private val viewModel: MainViewModel by activityViewModels()

    private val loadResultReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent) {
            when (intent.getStringExtra(DETAILS_LOAD_RESULT_EXTRA)) {
                DETAILS_INTENT_EMPTY_EXTRA -> fragmentMovieDetails.showSnackBar("test")
                DETAILS_DATA_EMPTY_EXTRA -> TODO(PROCESS_ERROR)
                DETAILS_RESPONSE_EMPTY_EXTRA -> TODO(PROCESS_ERROR)
                DETAILS_REQUEST_ERROR_EXTRA -> TODO(PROCESS_ERROR)
                DETAILS_REQUEST_ERROR_MESSAGE_EXTRA -> TODO(PROCESS_ERROR)
                DETAILS_URL_MALFORMED_EXTRA -> TODO(PROCESS_ERROR)
                DETAILS_RESPONSE_SUCCESS_EXTRA -> intent.getParcelableExtra<MovieDTO>(DETAILS_EXTRA)
                    ?.let { renderData(it) }
                else -> TODO(PROCESS_ERROR)
            }
        }
    }

    private fun renderData(movieDTO: MovieDTO) {
        with(binding, {
            detailsFragmentLoadingLayout.visibility = View.GONE
            fragmentMovieDetails.visibility = View.VISIBLE

            movieName.text = movieDTO.title
            movieOverview.text = movieDTO.overview
            movieGenre.text = movieDTO.genres?.get(0)?.name
            movieReleaseDate.text = movieDTO.release_date
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context?.let {
            LocalBroadcastManager.getInstance(it).registerReceiver(
                loadResultReceiver,
                IntentFilter(DETAILS_INTENT_FILTER)
            )
        }
    }

    override fun onDestroy() {
        context?.let {
            LocalBroadcastManager.getInstance(it).unregisterReceiver(loadResultReceiver)
        }
        super.onDestroy()
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
            val movie = it
            getMovie(movie)
        })
    }

    private fun getMovie(movie: Movie) {
        detailsFragmentLoadingLayout.visibility = View.VISIBLE
        fragmentMovieDetails.visibility = View.GONE

        val intent = Intent(requireContext(), DetailsService::class.java).apply {
            putExtra(MOVIE_ID, movie.id)
        }
        requireContext().startService(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun View.showSnackBar(
        text: String
    ) {
        Snackbar.make(this, text, Snackbar.LENGTH_SHORT)
            .show()
    }
}
