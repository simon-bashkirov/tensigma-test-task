package com.testtask.ui.fragment.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import com.testtask.R
import com.testtask.databinding.FragmentMainBinding
import com.testtask.ui.state.ProgressState
import org.koin.android.viewmodel.ext.android.viewModel

class MainFragment : Fragment() {

    private var binding: FragmentMainBinding? = null

    private val viewModel: MainViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = DataBindingUtil.inflate<FragmentMainBinding>(
        inflater, R.layout.fragment_main, container, false
    )
        .apply { lifecycleOwner = viewLifecycleOwner }
        .also { binding = it }
        .root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.viewModel = viewModel
        val transactionsAdapter = TransactionsAdapter()
        binding?.transactionsRecyclerView?.apply {
            itemAnimator = DefaultItemAnimator()
            adapter = transactionsAdapter
        }
        viewModel.transactions.observe(viewLifecycleOwner, Observer {
            transactionsAdapter.submitList(it)
        })

        viewModel.progressStateLiveData.observe(viewLifecycleOwner, Observer {
            if (it is ProgressState.Error) {
                Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
