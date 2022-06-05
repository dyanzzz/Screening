package com.suitmedia.screeningtest.features.screentwo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.suitmedia.screeningtest.databinding.FragmentDashboardBinding
import com.suitmedia.screeningtest.di.Injectable
import com.suitmedia.screeningtest.di.injectViewModel
import com.suitmedia.screeningtest.features.screenthree.EventFragment
import com.suitmedia.screeningtest.features.screenthree.EventViewModel
import timber.log.Timber
import javax.inject.Inject

class DashboardFragment: Fragment(), Injectable {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: EventViewModel
    private var eventName = ""

    private val args :DashboardFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        context ?: return binding.root

        viewModel = injectViewModel(viewModelFactory)

        val argsProfileEntity  = args.profileEntity

        binding.apply {
            name.text = "${argsProfileEntity?.name}!"

            setFragmentResultListener(EventFragment.EVENT){ _, resultEventName ->
                resultEventName.getString(EventFragment.EVENT_NAME).let { event ->
                    eventName = "Choose Event ${event.toString()}"
                    chooseEvent.text = eventName
                }
            }
            if(eventName.isNotEmpty()) chooseEvent.text = eventName

            chooseEvent.setOnClickListener {
                val direction = DashboardFragmentDirections.actionNavigationDashboardToNavigationEvent()
                it.findNavController().navigate(direction)
            }

            chooseGuest.setOnClickListener {
                val direction = DashboardFragmentDirections.actionNavigationDashboardToNavigationGuest()
                it.findNavController().navigate(direction)
            }
        }

        Timber.e(argsProfileEntity.toString())

        return binding.root
    }
}