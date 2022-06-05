package com.suitmedia.screeningtest.features.screenfour

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.suitmedia.screeningtest.R
import com.suitmedia.screeningtest.databinding.FragmentGuestBinding
import com.suitmedia.screeningtest.databinding.ToolbarBinding
import com.suitmedia.screeningtest.di.Injectable
import com.suitmedia.screeningtest.ui.setToolbar

class GuestFragment: Fragment(), Injectable {
    private var _binding: FragmentGuestBinding? = null
    private val binding get() = _binding!!
    private lateinit var toolbarBinding: ToolbarBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGuestBinding.inflate(inflater, container, false)
        toolbarBinding = ToolbarBinding.bind(binding.root)
        context ?: return binding.root

        setHasOptionsMenu(true)

        initToolbar()

        return binding.root
    }

    private fun initToolbar() {
        val toolbar = toolbarBinding.toolbar
        toolbar.setNavigationIcon(R.drawable.ic_back_white)

        setToolbar(toolbar, "Guest")
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