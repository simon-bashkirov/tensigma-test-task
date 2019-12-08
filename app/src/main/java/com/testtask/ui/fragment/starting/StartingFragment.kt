package com.testtask.ui.fragment.starting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.testtask.R
import com.testtask.databinding.FragmentStartingBinding
import org.koin.android.viewmodel.ext.android.viewModel

class StartingFragment : Fragment() {

    private var binding: FragmentStartingBinding? = null

    private val viewModel: StartingViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = DataBindingUtil.inflate<FragmentStartingBinding>(
        inflater, R.layout.fragment_starting, container, false
    )
        .apply { lifecycleOwner = viewLifecycleOwner }
        .also { binding = it }
        .root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.viewModel = viewModel
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
