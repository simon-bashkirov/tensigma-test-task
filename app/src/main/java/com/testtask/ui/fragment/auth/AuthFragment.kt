package com.testtask.ui.fragment.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.testtask.R
import com.testtask.databinding.FragmentAuthBinding
import com.testtask.ui.state.ProgressState
import org.koin.android.viewmodel.ext.android.viewModel

class AuthFragment : Fragment() {

    private var binding: FragmentAuthBinding? = null

    private val viewModel: AuthViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = DataBindingUtil.inflate<FragmentAuthBinding>(
        inflater, R.layout.fragment_auth, container, false
    )
        .apply { lifecycleOwner = viewLifecycleOwner }
        .also { binding = it }
        .root


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.viewModel = viewModel
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
