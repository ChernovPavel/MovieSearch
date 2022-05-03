package com.example.moviesearch.view.settings

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.moviesearch.databinding.FragmentSettingsBinding

const val IS_RUSSIAN_LANGUAGE = "IS_RUSSIAN_LANGUAGE"

class SettingsFragment : Fragment() {

    companion object {
        fun newInstance() = SettingsFragment()
    }

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