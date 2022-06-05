package com.suitmedia.screeningtest.features.screenthree

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.suitmedia.screeningtest.R
import com.suitmedia.screeningtest.databinding.FragmentEventBinding
import com.suitmedia.screeningtest.databinding.ToolbarBinding
import com.suitmedia.screeningtest.di.Injectable
import com.suitmedia.screeningtest.di.injectViewModel
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

    companion object {
        const val EVENT = "event"
        const val EVENT_NAME = "event_name"
    }

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
        initToolbar()

        return binding.root
    }

    private fun initToolbar() {
        val toolbar = toolbarBinding.toolbar
        toolbar.setNavigationIcon(R.drawable.ic_back_white)

        setToolbar(toolbar, "Event")
    }

    private fun initComponent() {
        viewModel.setListEvent()
        adapter = EventAdapter()
        adapter.notifyDataSetChanged()

        binding.apply {
            rv.setHasFixedSize(true)
            rv.layoutManager = LinearLayoutManager(activity)
            rv.adapter = adapter
        }

        adapter.setEventCallback(object : EventCallback{
            override fun onItemClicked(data: EventEntity) {
                setFragmentResult(
                    EVENT, bundleOf(
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.event_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
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
                Toast.makeText(activity, "Map", Toast.LENGTH_LONG).show()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
