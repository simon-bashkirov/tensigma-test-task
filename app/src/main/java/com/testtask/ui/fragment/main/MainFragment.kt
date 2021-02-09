package com.testtask.ui.fragment.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DefaultItemAnimator
import com.testtask.R
import com.testtask.databinding.FragmentMainBinding
import com.testtask.ui.base.BaseFragment
import org.koin.android.viewmodel.ext.android.viewModel

class MainFragment : BaseFragment() {

    override val viewModel: MainViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = DataBindingUtil.inflate<FragmentMainBinding>(
        inflater, R.layout.fragment_main, container, false
    ).apply {
        lifecycleOwner = viewLifecycleOwner
        val transactionsAdapter = TransactionsAdapter()
        transactionsRecyclerView.apply {
            itemAnimator = DefaultItemAnimator()
            adapter = transactionsAdapter
        }
        viewModel = this@MainFragment.viewModel.apply {
            transactions.observe(viewLifecycleOwner) {
                transactionsAdapter.submitList(it)
                transactionsRecyclerView.smoothScrollToPosition(transactionsAdapter.itemCount)
            }
        }
    }.root
}
