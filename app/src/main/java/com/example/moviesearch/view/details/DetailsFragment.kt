package com.example.moviesearch.view.details

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.moviesearch.BuildConfig
import com.example.moviesearch.R
import com.example.moviesearch.databinding.FragmentDetailsBinding
import com.example.moviesearch.model.Movie
import com.example.moviesearch.model.MovieDTO
import com.example.moviesearch.viewmodel.MainViewModel
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_details.*
import okhttp3.*
import java.io.IOException

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

    // запрашиваем ViewModel активити. Чтобы на несколько фрагментов создавалась одна ViewModel
    private val viewModel: MainViewModel by activityViewModels()

    private val loadResultReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent) {
            when (intent.getStringExtra(DETAILS_LOAD_RESULT_EXTRA)) {
                DETAILS_INTENT_EMPTY_EXTRA -> showToast(getString(R.string.movie_id_not_sent))
                DETAILS_DATA_EMPTY_EXTRA -> showToast(getString(R.string.invalid_movie_id))
                DETAILS_RESPONSE_EMPTY_EXTRA -> showToast(getString(R.string.no_data_for_movie))
                DETAILS_REQUEST_ERROR_EXTRA -> showToast(getString(R.string.invalid_data_for_movie))
                DETAILS_REQUEST_ERROR_MESSAGE_EXTRA -> showToast(getString(R.string.invalid_data_for_movie))
                DETAILS_URL_MALFORMED_EXTRA -> showToast(getString(R.string.invalid_URL))
                DETAILS_RESPONSE_SUCCESS_EXTRA -> intent.getParcelableExtra<MovieDTO>(DETAILS_EXTRA)
                    ?.let { renderData(it) }
                else -> showToast(getString(R.string.network_error))
            }
        }
    }

    private fun renderData(movieDTO: MovieDTO) {
        with(binding, {
            detailsFragmentLoadingLayout.visibility = View.GONE
            fragmentMovieDetails.visibility = View.VISIBLE

            if (movieDTO.title == null ||
                movieDTO.overview == null ||
                movieDTO.release_date == null ||
                movieDTO.genres?.get(0)?.name == null
            ) {
                TODO(PROCESS_ERROR)
            } else {
                movieName.text = movieDTO.title
                movieOverview.text = movieDTO.overview
                movieGenre.text = movieDTO.genres[0].name
                movieReleaseDate.text = movieDTO.release_date
            }
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

        val client = OkHttpClient()
        val builder: Request.Builder = Request.Builder()
        builder.url(MAIN_LINK + movie.id + "?api_key=${BuildConfig.MOVIE_API_KEY}&language=ru")
        val request: Request = builder.build()
        val call: Call = client.newCall(request)

        call.enqueue(object : Callback {

            val handler: Handler = Handler()

            override fun onResponse(call: Call?, response: Response) {
                val serverResponse: String? = response.body()?.string()

                if (response.isSuccessful && serverResponse != null) {
                    handler.post {
                        renderData(Gson().fromJson(serverResponse, MovieDTO::class.java))
                    }
                } else {
                    TODO(PROCESS_ERROR)
                }
            }

            override fun onFailure(call: Call, e: IOException) {
                TODO("Not yet implemented")
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}
