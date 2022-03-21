package com.example.moviesearch.view.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.moviesearch.databinding.FragmentDetailsBinding
import com.example.moviesearch.model.Movie


class DetailsFragment : Fragment() {

    var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val movie = arguments?.getParcelable<Movie>(BUNDLE_EXTRA)
        if (movie != null) {
            binding.movieName.text = movie.name
            binding.movieDescription.text = movie.description
            binding.movieGenre.text = movie.genre
            binding.movieReleaseDate.text = movie.release_date
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val BUNDLE_EXTRA = "movie"

        fun newInstance(bundle: Bundle): DetailsFragment {

            val fragment = DetailsFragment()
            fragment.arguments = bundle
            return fragment
        }
    }
}
