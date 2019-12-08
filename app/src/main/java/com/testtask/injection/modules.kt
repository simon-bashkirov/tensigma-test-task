package com.testtask.injection

import com.testtask.data.repository.AuthRepositoryImpl
import com.testtask.data.repository.UserRepsitoryImpl
import com.testtask.domain.repository.AuthRepository
import com.testtask.domain.repository.UserRepository
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

val domainModule = module {

    single<UserRepository> { UserRepsitoryImpl() }

    single<AuthRepository> { AuthRepositoryImpl() }

}

