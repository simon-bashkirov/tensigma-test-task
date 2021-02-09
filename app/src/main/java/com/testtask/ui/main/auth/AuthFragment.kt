package com.testtask.ui.main.auth

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.databinding.DataBindingUtil
import com.testtask.R
import com.testtask.databinding.FragmentAuthBinding
import com.testtask.ui.BaseFragment
import com.testtask.utils.state.ProgressState
import org.koin.android.viewmodel.ext.android.viewModel

class AuthFragment : BaseFragment() {

    override val viewModel: AuthViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = DataBindingUtil.inflate<FragmentAuthBinding>(
        inflater, R.layout.fragment_auth, container, false
    ).apply {
        lifecycleOwner = viewLifecycleOwner
        viewModel = this@AuthFragment.viewModel.apply {
            progressState.observe(viewLifecycleOwner) {
                if (it is ProgressState.Progress) hideSoftKeyboard()
            }
        }
    }.root

    private fun hideSoftKeyboard() {
        val imm =
            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
                ?: return
        val view = view ?: return
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}
