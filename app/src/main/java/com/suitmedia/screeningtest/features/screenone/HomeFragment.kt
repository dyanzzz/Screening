package com.suitmedia.screeningtest.features.screenone

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.suitmedia.screeningtest.databinding.FragmentHomeBinding
import com.suitmedia.screeningtest.di.Injectable

class HomeFragment: Fragment(), Injectable {
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        context ?: return binding.root

        return binding.root
    }
}