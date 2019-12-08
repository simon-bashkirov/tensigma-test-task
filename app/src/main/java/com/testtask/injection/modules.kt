package com.testtask.injection

import com.testtask.ui.activity.MainActivityViewModel
import com.testtask.ui.fragment.auth.AuthViewModel
import com.testtask.ui.fragment.main.MainViewModel
import com.testtask.ui.fragment.starting.StartingViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val uiModule = module {

    viewModel { StartingViewModel() }

    viewModel { AuthViewModel() }

    viewModel { MainViewModel() }

    viewModel { MainActivityViewModel() }
}

