package com.testtask.ui.fragment.starting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.testtask.R
import com.testtask.databinding.FragmentStartingBinding

class StartingFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = DataBindingUtil.inflate<FragmentStartingBinding>(
        inflater, R.layout.fragment_starting, container, false
    ).root

}
