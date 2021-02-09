package com.testtask.ui.main.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DefaultItemAnimator
import com.testtask.R
import com.testtask.databinding.FragmentDashboardBinding
import com.testtask.ui.BaseFragment
import com.testtask.ui.main.dashboard.model.TransactionItem
import com.testtask.utils.view.rv.UniformListAdapter
import org.koin.android.viewmodel.ext.android.viewModel

class DashboardFragment : BaseFragment() {

    override val viewModel: DashboardViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = DataBindingUtil.inflate<FragmentDashboardBinding>(
        inflater, R.layout.fragment_dashboard, container, false
    ).apply {
        lifecycleOwner = viewLifecycleOwner
        val transactionsAdapter =
            UniformListAdapter(R.layout.item_transaction, TransactionItem::hashOutput)
        transactionsRecyclerView.apply {
            itemAnimator = DefaultItemAnimator()
            adapter = transactionsAdapter
        }
        viewModel = this@DashboardFragment.viewModel.apply {
            transactions.observe(viewLifecycleOwner) {
                transactionsAdapter.submitList(it)
                transactionsRecyclerView.smoothScrollToPosition(transactionsAdapter.itemCount)
            }
        }
    }.root
}
