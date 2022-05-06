package com.example.moviesearch.view.details

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.moviesearch.R
import com.example.moviesearch.app.App
import com.example.moviesearch.databinding.FragmentDetailsBinding
import com.example.moviesearch.model.Movie
import com.example.moviesearch.utils.hideKeyboard
import com.example.moviesearch.utils.showSnackBar
import com.example.moviesearch.viewmodel.AppState
import com.example.moviesearch.viewmodel.DetailsViewModel
import com.example.moviesearch.viewmodel.DetailsViewModelFactory
import com.squareup.picasso.Picasso
import javax.inject.Inject

class DetailsFragment : Fragment() {

    companion object {
        const val MOVIE_ID = "movieId"
        fun newInstance(movieId: Int): DetailsFragment {
            val fragment = DetailsFragment()
            val bundle = Bundle()
            bundle.putInt(MOVIE_ID, movieId)
            fragment.arguments = bundle
            return fragment
        }
    }

    @Inject
    lateinit var dvmFactory: DetailsViewModelFactory

    var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!
    private var movieId: Int = -1
    private val viewModel: DetailsViewModel by viewModels { dvmFactory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        (requireActivity().applicationContext as App).appComponent.inject(this)
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("RestrictedApi")
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as AppCompatActivity).supportActionBar?.apply {
            setShowHideAnimationEnabled(false)
            hide()
        }

        movieId = arguments?.getInt(MOVIE_ID) ?: -1

        viewModel.detailsLiveData.observe(viewLifecycleOwner) { renderData(it) }
        viewModel.noteLiveData.observe(viewLifecycleOwner) { binding.noteEditText.setText(it) }

        viewModel.getMovieFromAPI(movieId)
        viewModel.getNoteFromDB(movieId)

        binding.noteSaveButton.setOnClickListener {
            viewModel.saveNoteToDB(binding.noteEditText.text.toString(), movieId)
            Toast.makeText(context, R.string.note_saved, Toast.LENGTH_SHORT).show()
            hideKeyboard()
        }
    }

    private fun setMovie(movie: Movie) {

        viewModel.saveMovieToDB(movie)

        with(binding) {
            movieName.text = movie.title
            collapsingToolbar.title = movie.title
            movieOverview.text = movie.overview
            movieGenre.text = movie.genre
            movieReleaseDate.text = movie.release_date
            Picasso
                .get()
                .load("https://image.tmdb.org/t/p/w500${movie.backdrop_path}?language=ru")
                .into(imageCollapsingToolbar)

        }
    }

    private fun renderData(appState: AppState) {

        when (appState) {
            is AppState.Success -> {
                binding.includedLoadingLayout.loadingLayout.visibility = View.GONE
                binding.fragmentMovieDetails.visibility = View.VISIBLE
                setMovie(appState.movieData[0])
            }
            is AppState.Loading -> {
                binding.includedLoadingLayout.loadingLayout.visibility = View.VISIBLE
                binding.fragmentMovieDetails.visibility = View.GONE
            }
            is AppState.Error -> {
                binding.includedLoadingLayout.loadingLayout.visibility = View.GONE
                binding.fragmentMovieDetails.visibility = View.VISIBLE
                binding.fragmentMovieDetails.showSnackBar(
                    getString(R.string.error),
                    getString(R.string.reload),
                    {
                        viewModel.getMovieFromAPI(movieId)
                    })
            }
        }
    }

    override fun onDestroyView() {
        (requireActivity() as AppCompatActivity).supportActionBar?.show()
        _binding = null
        super.onDestroyView()
    }
}
