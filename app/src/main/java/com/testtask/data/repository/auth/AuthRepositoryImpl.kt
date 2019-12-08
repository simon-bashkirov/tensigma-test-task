package com.testtask.data.repository.auth

import com.testtask.domain.repository.AuthRepository
import com.testtask.domain.state.AuthState
import io.reactivex.Completable
import io.reactivex.processors.BehaviorProcessor

class AuthRepositoryImpl(private val authDataSource: AuthDataSource) : AuthRepository {

    private val authStatePublisher = BehaviorProcessor.create<AuthState>()
        .apply {
            //TODO in progress
            onNext(AuthState.UnAuthorized)
        }

    override fun signIn(email: String, password: String) =
        authDataSource.signIn(email, password)
            .doOnComplete {
                authStatePublisher.onNext(AuthState.Authorized)
            }


    override fun signOut() = authDataSource.signOut()
        .doOnComplete { authStatePublisher.onNext(AuthState.UnAuthorized) } as Completable

    override fun getAuthState() = authStatePublisher
}