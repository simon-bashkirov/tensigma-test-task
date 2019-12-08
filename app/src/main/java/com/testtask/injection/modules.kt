package com.testtask.injection

import com.testtask.data.repository.AuthRepositoryImpl
import com.testtask.data.repository.UserRepsitoryImpl
import com.testtask.domain.interactor.auth.SignInUseCase
import com.testtask.domain.interactor.auth.SignOutUseCase
import com.testtask.domain.interactor.user.ObserveMyFirstProfileUseCase
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

    viewModel {
        AuthViewModel(
            signInUseCase = get()
        )
    }

    viewModel {
        MainViewModel(
            observeMyFirstProfileUseCase = get(),
            signOutUseCase = get()
        )
    }

    viewModel {
        MainActivityViewModel(
            observeAuthStateUse = get()
        )
    }
}

val domainModule = module {

    single<UserRepository> { UserRepsitoryImpl() }

    single<AuthRepository> { AuthRepositoryImpl() }

    single { ObserveMyFirstProfileUseCase(userRepository = get()) }

    single { SignInUseCase(authRepository = get()) }

    single { SignOutUseCase(authRepository = get()) }

}

