package com.example.moviesearch.view.settings

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.moviesearch.databinding.FragmentSettingsBinding
import com.example.moviesearch.viewmodel.MainViewModel

const val IS_RUSSIAN_LANGUAGE = "IS_RUSSIAN_LANGUAGE"

class SettingsFragment : Fragment() {

    companion object {
        fun newInstance() = SettingsFragment()
    }

    private lateinit var viewModel: MainViewModel
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        activity?.let {
            binding.changeLanguageSwitch.isChecked =
                it.getPreferences(Context.MODE_PRIVATE)
                    .getBoolean(IS_RUSSIAN_LANGUAGE, false)
        }

        binding.changeLanguageSwitch.setOnCheckedChangeListener { _, isChecked ->
            val editor = activity?.getPreferences(Context.MODE_PRIVATE)?.edit()
            editor?.putBoolean(IS_RUSSIAN_LANGUAGE, isChecked)
            editor?.apply()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}