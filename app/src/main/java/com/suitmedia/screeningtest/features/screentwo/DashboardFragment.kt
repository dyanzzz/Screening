package com.suitmedia.screeningtest.features.screentwo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.suitmedia.screeningtest.databinding.FragmentDashboardBinding
import com.suitmedia.screeningtest.di.Injectable
import timber.log.Timber

class DashboardFragment: Fragment(), Injectable {
    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    private val args :DashboardFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        context ?: return binding.root

        val argsProfileEntity  = args.profileEntity
        binding.textView.text = argsProfileEntity?.name

        Timber.e(argsProfileEntity.toString())

        return binding.root
    }
}