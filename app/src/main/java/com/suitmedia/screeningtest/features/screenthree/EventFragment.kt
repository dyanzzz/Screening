package com.suitmedia.screeningtest.features.screenthree

import android.location.Location
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker
import com.suitmedia.screeningtest.R
import com.suitmedia.screeningtest.databinding.FragmentEventBinding
import com.suitmedia.screeningtest.databinding.ToolbarBinding
import com.suitmedia.screeningtest.di.Injectable
import com.suitmedia.screeningtest.di.injectViewModel
import com.suitmedia.screeningtest.features.screentwo.DashboardFragment.Companion.EVENT_NAME
import com.suitmedia.screeningtest.features.screentwo.DashboardFragment.Companion.RETURN_VALUE
import com.suitmedia.screeningtest.ui.setToolbar
import javax.inject.Inject

class EventFragment: Fragment(), Injectable {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private var _binding: FragmentEventBinding? = null
    private val binding get() = _binding!!
    private lateinit var toolbarBinding: ToolbarBinding
    private lateinit var viewModel: EventViewModel
    private lateinit var adapter: EventAdapter
    private lateinit var mapMenu: MenuItem
    private lateinit var listMenu: MenuItem

    private lateinit var mMap: GoogleMap
    private lateinit var mGoogleApiClient: GoogleApiClient
    private lateinit var mLocationRequest: LocationRequest
    private lateinit var mCurrLocationMarker: Marker
    private lateinit var mLastLocation: Location

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEventBinding.inflate(inflater, container, false)
        toolbarBinding = ToolbarBinding.bind(binding.root)
        context ?: return binding.root

        viewModel = injectViewModel(viewModelFactory)

        setHasOptionsMenu(true)

        initComponent()
        initMapComponent()
        initToolbar()

        return binding.root
    }

    private fun initToolbar() {
        val toolbar = toolbarBinding.toolbar
        toolbar.setNavigationIcon(R.drawable.ic_back_white)

        setToolbar(toolbar, "Events")
    }

    private fun initComponent() {
        adapter = EventAdapter()
        adapter.notifyDataSetChanged()
        viewModel.setListEvent()

        binding.apply {
            rv.setHasFixedSize(true)
            rv.layoutManager = LinearLayoutManager(activity)
            rv.adapter = adapter
        }

        adapter.setEventCallback(object : EventCallback{
            override fun onItemClicked(data: EventEntity) {
                setFragmentResult(
                    RETURN_VALUE, bundleOf(
                        EVENT_NAME to data.title
                    )
                )
                findNavController().popBackStack()
            }
        })

        viewModel.getListEvent().observe(viewLifecycleOwner) {
            if (it != null) {
                adapter.setList(it)
            }
        }
    }

    private fun initMapComponent() {
        binding.apply {
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.event_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
        mapMenu = menu.findItem(R.id.map_menu)
        listMenu = menu.findItem(R.id.list_menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> {
                if (!findNavController().popBackStack()) activity?.finish()
            }
            R.id.search_menu -> {
                Toast.makeText(activity, "Button Search can’t clicked just dummy icon", Toast.LENGTH_LONG).show()
            }
            R.id.map_menu -> {
                binding.rv.visibility = View.GONE
                binding.mapFramelayout.visibility = View.VISIBLE
                mapMenu.isVisible = false
                listMenu.isVisible = true
                Toast.makeText(activity, "Map", Toast.LENGTH_LONG).show()
            }
            R.id.list_menu -> {
                binding.rv.visibility = View.VISIBLE
                binding.mapFramelayout.visibility = View.GONE
                mapMenu.isVisible = true
                listMenu.isVisible = false
                Toast.makeText(activity, "List", Toast.LENGTH_LONG).show()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
