package com.testtask.injection

import com.testtask.R
import com.testtask.data.local.AuthLocalDataSourceImpl
import com.testtask.data.local.AuthLostObserverImpl
import com.testtask.data.local.AuthTokenProviderImpl
import com.testtask.data.local.LocalStorage
import com.testtask.data.local.pref.StringPrefLocalStorage
import com.testtask.data.remote.AuthRemoteDataSourceImpl
import com.testtask.data.remote.UserDataSourceImpl
import com.testtask.data.remote.rest.adapter.RestAdapter
import com.testtask.data.remote.rest.adapter.impl.RetrofitRestAdapter
import com.testtask.data.repository.auth.*
import com.testtask.data.repository.user.UserDataSource
import com.testtask.data.repository.user.UserRepositoryImpl
import com.testtask.domain.interactor.auth.ObserveAuthStateUseCase
import com.testtask.domain.interactor.auth.SignInUseCase
import com.testtask.domain.interactor.auth.SignOutUseCase
import com.testtask.domain.interactor.user.ObserveMyFirstProfileUseCase
import com.testtask.domain.repository.AuthRepository
import com.testtask.domain.repository.UserRepository
import com.testtask.ui.activity.MainActivityViewModel
import com.testtask.ui.fragment.auth.AuthViewModel
import com.testtask.ui.fragment.main.MainViewModel
import com.testtask.ui.fragment.starting.StartingViewModel
import org.koin.android.ext.koin.androidApplication
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

val dataModule = module {

    single<LocalStorage<String>> {
        StringPrefLocalStorage(
            appContext = androidApplication()
        )
    }

    single<AuthLocalDataSource> {
        AuthLocalDataSourceImpl(stringLocalStorage = get())
    }

    single<AuthTokenProvider> {
        AuthTokenProviderImpl(
            authLocalSource = get()
        )
    }

    single<AuthRemoteDataSource> {
        AuthRemoteDataSourceImpl(
            restAdapter = get(),
            authTokenProvider = get()
        )
    }

    single<AuthLostObserver> {
        AuthLostObserverImpl(
            userRepository = get()
        )
    }

    single<RestAdapter> {
        RetrofitRestAdapter(
            apiBaseUrl = androidApplication().getString(R.string.api_base_url)
        )
    }


    single<UserDataSource> {
        UserDataSourceImpl(
            restAdapter = get(),
            tokenProvider = get()
        )
    }
}

val domainModule = module {

    single<UserRepository> {
        UserRepositoryImpl(
            userDataSource = get()
        )
    }

    single<AuthRepository> {
        AuthRepositoryImpl(
            authRemoteDataSource = get(),
            authLocalDataSource = get(),
            authLostObserver = get()
        )
    }

    single { ObserveMyFirstProfileUseCase(userRepository = get()) }

    single { ObserveAuthStateUseCase(authRepository = get()) }

    single { SignInUseCase(authRepository = get()) }

    single { SignOutUseCase(authRepository = get()) }

}

