package com.testtask.ui.base

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.CallSuper
import androidx.fragment.app.Fragment
import com.testtask.ui.state.ProgressState

abstract class BaseFragment : Fragment() {

    protected abstract val viewModel: BaseViewModel

    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.progressState.observe(viewLifecycleOwner) {
            if (it is ProgressState.Error) {
                activity?.apply { Toast.makeText(this, it.message, Toast.LENGTH_LONG).show() }
            }
        }
    }
}