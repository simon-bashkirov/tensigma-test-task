package com.testtask

import com.testtask.data.local.AuthLocalDataSourceImpl
import com.testtask.data.local.AuthLostObserverImpl
import com.testtask.data.local.AuthTokenProviderImpl
import com.testtask.data.local.LocalStorage
import com.testtask.data.local.pref.StringPrefLocalStorage
import com.testtask.data.remote.rest.AuthRemoteDataSourceImpl
import com.testtask.data.remote.rest.UserDataSourceImpl
import com.testtask.data.remote.rest.service.ApiServiceFactory
import com.testtask.data.remote.rest.service.impl.RetrofitApiServiceFactory
import com.testtask.data.remote.wss.TransactionWssDataSource
import com.testtask.data.remote.wss.mapper.SocketMapperFactory
import com.testtask.data.remote.wss.mapper.gson.GsonSocketMapperFactory
import com.testtask.data.remote.wss.service.SocketServiceFactory
import com.testtask.data.remote.wss.service.impl.SocketServiceFactoryImpl
import com.testtask.data.repository.auth.*
import com.testtask.data.repository.transaction.TransactionDataSource
import com.testtask.data.repository.transaction.TransactionRepositoryImpl
import com.testtask.data.repository.user.UserDataSource
import com.testtask.data.repository.user.UserRepositoryImpl
import com.testtask.domain.interactor.auth.ObserveAuthStateUseCase
import com.testtask.domain.interactor.auth.SignInUseCase
import com.testtask.domain.interactor.auth.SignOutUseCase
import com.testtask.domain.interactor.transaction.ClearTransactionsUseCase
import com.testtask.domain.interactor.transaction.ObserveTransactionUpdatesUseCase
import com.testtask.domain.interactor.transaction.StartTransactionsUseCase
import com.testtask.domain.interactor.transaction.StopTransactionsUseCase
import com.testtask.domain.interactor.user.ObserveMyFirstProfileUseCase
import com.testtask.domain.repository.AuthRepository
import com.testtask.domain.repository.TransactionRepository
import com.testtask.domain.repository.UserRepository
import com.testtask.ui.main.MainViewModel
import com.testtask.ui.main.auth.AuthViewModel
import com.testtask.ui.main.dashboard.DashboardViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val uiModule = module {

    viewModel {
        AuthViewModel(
            signInUseCase = get()
        )
    }

    viewModel {
        DashboardViewModel(
            observeTransactionUpdatesUseCase = get(),
            observeMyFirstProfileUseCase = get(),
            signOutUseCase = get(),
            startTransactionsUseCase = get(),
            stopTransactionsUseCase = get(),
            clearTransactionsUseCase = get()
        )
    }

    viewModel {
        MainViewModel(
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
            apiServiceFactory = get()
        )
    }

    single<AuthLostObserver> {
        AuthLostObserverImpl(
            userRepository = get(),
            transactionRepository = get()
        )
    }

    single<ApiServiceFactory> {
        RetrofitApiServiceFactory(
            apiBaseUrl = androidApplication().getString(R.string.api_base_url),
            authTokenProvider = get()
        )
    }

    single<UserDataSource> {
        UserDataSourceImpl(
            apiServiceFactory = get()
        )
    }

    single<TransactionDataSource> {
        TransactionWssDataSource(
            serviceFactory = get(),
            mapperFactory = get()
        )
    }

    single<SocketServiceFactory> {
        SocketServiceFactoryImpl(
            wssBaseUrl = androidApplication().getString(R.string.wss_base_url),
            mapperFactory = get()
        )
    }

    single<SocketMapperFactory> {
        GsonSocketMapperFactory()
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

    single<TransactionRepository> {
        TransactionRepositoryImpl(
            transactionDataSource = get()
        )
    }

    single { ObserveMyFirstProfileUseCase(userRepository = get()) }

    single { ObserveAuthStateUseCase(authRepository = get()) }

    single { SignInUseCase(authRepository = get()) }

    single { SignOutUseCase(authRepository = get()) }

    single { StartTransactionsUseCase(transactionRepository = get()) }

    single { StopTransactionsUseCase(transactionRepository = get()) }

    single { ClearTransactionsUseCase(transactionRepository = get()) }

    single { ObserveTransactionUpdatesUseCase(transactionRepository = get()) }

}

