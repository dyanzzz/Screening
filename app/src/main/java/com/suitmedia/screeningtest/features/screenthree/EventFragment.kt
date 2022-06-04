package com.suitmedia.screeningtest.features.screenthree

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.suitmedia.screeningtest.databinding.FragmentEventBinding
import com.suitmedia.screeningtest.di.Injectable

class EventFragment: Fragment(), Injectable {
    private var _binding: FragmentEventBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEventBinding.inflate(inflater, container, false)
        context ?: return binding.root

        return binding.root
    }
}