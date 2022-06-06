package com.suitmedia.screeningtest.features.screenfour

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.suitmedia.screeningtest.R
import com.suitmedia.screeningtest.data.Result
import com.suitmedia.screeningtest.databinding.FragmentGuestBinding
import com.suitmedia.screeningtest.databinding.ToolbarBinding
import com.suitmedia.screeningtest.di.Injectable
import com.suitmedia.screeningtest.di.injectViewModel
import com.suitmedia.screeningtest.features.screenone.ProfileEntity
import com.suitmedia.screeningtest.features.screentwo.DashboardFragment.Companion.FULL_NAME
import com.suitmedia.screeningtest.features.screentwo.DashboardFragment.Companion.RETURN_VALUE
import com.suitmedia.screeningtest.ui.setToolbar
import com.suitmedia.screeningtest.utils.SpacingItemDecoration
import com.suitmedia.screeningtest.utils.Tools
import com.suitmedia.screeningtest.utils.observeEvent
import timber.log.Timber
import javax.inject.Inject

class GuestFragment: Fragment(), Injectable {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private var _binding: FragmentGuestBinding? = null
    private val binding get() = _binding!!
    private lateinit var toolbarBinding: ToolbarBinding
    private lateinit var viewModel: GuestViewModel
    private lateinit var adapter: GuestAdapter
    private lateinit var layoutManager: LinearLayoutManager
    private var limit: Int = 10
    private var page: Int = 1
    private var totalPage: Int = 1
    private var isLoading = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGuestBinding.inflate(inflater, container, false)
        toolbarBinding = ToolbarBinding.bind(binding.root)
        context ?: return binding.root

        viewModel = injectViewModel(viewModelFactory)

        setHasOptionsMenu(true)

        initToolbar()
        initComponent()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        getListGuest()
    }

    private fun initToolbar() {
        val toolbar = toolbarBinding.toolbar
        toolbar.setNavigationIcon(R.drawable.ic_back_white)

        setToolbar(toolbar, "Guest")
    }

    private fun initComponent() {
        adapter = GuestAdapter()
        adapter.notifyDataSetChanged()

        try {
            adapter.clear()
            page = 1
            viewModel.setListGuest(page, limit)
        } catch(err: Exception) {
            Toast.makeText(requireContext(), err.message.toString(), Toast.LENGTH_LONG).show()
        }

        binding.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            rv.layoutManager = layoutManager
            rv.addItemDecoration(SpacingItemDecoration(2, Tools.dpToPx(requireContext(), 3), true))
            rv.setHasFixedSize(true)

            rv.adapter = adapter

            rv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    val visibleItemCount = layoutManager.childCount
                    val pastVisibleItem = layoutManager.findFirstVisibleItemPosition()
                    val total  = adapter.itemCount

                    if (!isLoading && page < totalPage){
                        if (visibleItemCount + pastVisibleItem >= total){
                            page++

                            viewModel.setListGuest(page, limit)
                            getListGuest()
                        }
                    }

                }
            })

            swipe.setOnRefreshListener {
                swipe.isRefreshing= true
                page = 1

                try {
                    adapter.clear()

                    viewModel.setListGuest(page, limit)
                    swipe.isRefreshing= false
                } catch (e: Exception){
                    Timber.e("Error setList : $e")
                    Toast.makeText(requireContext(), "Error : $e", Toast.LENGTH_LONG).show()
                }
            }
        }

        adapter.setGuestCallback(object : GuestCallback {
            override fun onItemClicked(data: ProfileEntity) {
                //Toast.makeText(requireContext(), data.first_name, Toast.LENGTH_LONG).show()
                setFragmentResult(
                    RETURN_VALUE, bundleOf(
                        FULL_NAME to data.first_name + " " + data.last_name
                    )
                )
                findNavController().popBackStack()
            }
        })
    }

    private fun getListGuest(){
        binding.apply {
            try {
                isLoading = true
                viewModel.getListGuestItem.observeEvent(viewLifecycleOwner) { resultObserve ->
                    resultObserve.observe(viewLifecycleOwner) { result ->
                        if (result != null) {
                            when (result.status) {
                                Result.Status.LOADING -> {
                                    Timber.d("###-- Loading get List SS")
                                    progressbar.visibility = View.VISIBLE
                                }
                                Result.Status.SUCCESS -> {
                                    progressbar.visibility = View.GONE
                                    val response = result.data
                                    totalPage = response!!.totalPage
                                    adapter.setList(response.data)
                                    isLoading = false
                                }
                                Result.Status.ERROR -> {
                                    isLoading = false
                                    progressbar.visibility = View.GONE
                                    Toast.makeText(
                                        requireContext(),
                                        "Error : ${result.message}",
                                        Toast.LENGTH_LONG
                                    ).show()
                                    Timber.d("###-- Error get List SS")
                                }
                            }
                        }
                    }
                }
            } catch (err: Exception) {
                isLoading = false
                Toast.makeText(
                    requireContext(),
                    "Error : ${err.message}",
                    Toast.LENGTH_LONG
                ).show()
                Timber.d("###-- Error get List SS")
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> {
                if (!findNavController().popBackStack()) activity?.finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}