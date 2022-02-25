package com.example.moviesearch.view.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.moviesearch.databinding.FragmentDetailsBinding
import com.example.moviesearch.viewmodel.MainViewModel


class DetailsFragment : Fragment() {

    var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    // запрашиваем ViewModel активити. Чтобы на несколько фрагментов создавалась одна ViewModel
    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //отображаем данные по фильму которые положили в переменную selectedItem при нажатии на item списка
        viewModel.selectedItem.observe(viewLifecycleOwner, { movie ->
            binding.movieName.text = movie.name
            binding.movieOverview.text = movie.overview
            binding.movieGenre.text = movie.genre
            binding.movieReleaseDate.text = movie.release_date
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
