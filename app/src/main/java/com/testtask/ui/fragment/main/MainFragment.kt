package com.testtask.ui.fragment.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import com.testtask.R
import com.testtask.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    private var binding: FragmentMainBinding? = null

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
        val viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        binding?.viewModel = viewModel
        val transactionsAdapter = TransactionsAdapter()
        binding?.transactionsRecyclerView?.apply {
            itemAnimator = DefaultItemAnimator()
            adapter = transactionsAdapter
        }
        viewModel.transactions.observe(
            viewLifecycleOwner,
            Observer { transactionsAdapter.submitList(it) })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
