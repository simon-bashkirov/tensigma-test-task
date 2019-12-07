package com.testtask.ui.fragment.starting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.testtask.R
import com.testtask.ui.fragment.StartingViewModel

class StartingFragment : Fragment() {

    companion object {
        fun newInstance() = StartingFragment()
    }

    private lateinit var viewModel: StartingViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_starting, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(StartingViewModel::class.java)

    }

}
