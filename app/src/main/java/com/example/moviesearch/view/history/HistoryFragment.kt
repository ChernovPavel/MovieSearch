package com.example.moviesearch.view.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DiffUtil
import com.example.moviesearch.R
import com.example.moviesearch.app.App
import com.example.moviesearch.databinding.FragmentHistoryBinding
import com.example.moviesearch.utils.showSnackBar
import com.example.moviesearch.viewmodel.AppState
import com.example.moviesearch.viewmodel.HistoryViewModel
import com.example.moviesearch.viewmodel.ViewModelFactory
import javax.inject.Inject

class HistoryFragment : Fragment() {

    companion object {
        fun newInstance() = HistoryFragment()
    }

    @Inject
    lateinit var hvmFactory: ViewModelFactory
    private val viewModel: HistoryViewModel by viewModels { hvmFactory }

    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!

    private val adapter: HistoryAdapter by lazy { HistoryAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (requireActivity().applicationContext as App).historyComponent.inject(this)
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.historyFragmentRecyclerview.adapter = adapter
        viewModel.historyLiveData.observe(viewLifecycleOwner) {
            renderData(it)
        }
        viewModel.getAllHistory()
    }

    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Success -> {
                binding.historyFragmentRecyclerview.visibility = View.VISIBLE
                binding.includedLoadingLayout.loadingLayout.visibility =
                    View.GONE
                val movieData = appState.movieData
                val historyDiffUtilCallback =
                    HistoryDiffUtilsCallback(adapter.historyData, movieData)
                val historyDiffResult = DiffUtil.calculateDiff(historyDiffUtilCallback)
                adapter.setData(movieData)
                historyDiffResult.dispatchUpdatesTo(adapter)
            }
            is AppState.Loading -> {
                binding.historyFragmentRecyclerview.visibility = View.GONE
                binding.includedLoadingLayout.loadingLayout.visibility =
                    View.VISIBLE
            }
            is AppState.Error -> {
                binding.historyFragmentRecyclerview.visibility = View.VISIBLE
                binding.includedLoadingLayout.loadingLayout.visibility =
                    View.GONE
                binding.historyFragmentRecyclerview.showSnackBar(
                    getString(R.string.error),
                    getString(R.string.reload),
                    {
                        viewModel.getAllHistory()
                    })
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}