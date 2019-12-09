package com.testtask.data.repository.auth

import com.testtask.domain.repository.AuthRepository
import com.testtask.domain.state.AuthState
import io.reactivex.Completable
import io.reactivex.processors.BehaviorProcessor

class AuthRepositoryImpl(
    private val authRemoteDataSource: AuthRemoteDataSource,
    private val authLocalDataSource: AuthLocalDataSource
) : AuthRepository {

    private val authStatePublisher = BehaviorProcessor.create<AuthState>()
        .apply {
            if (authLocalDataSource.hasToken() && !authLocalDataSource.isExpired()) {
                onNext(AuthState.Authorized)
            } else {
                onNext(AuthState.UnAuthorized)
            }
        }

    override fun signIn(email: String, password: String) =
        authRemoteDataSource.requestToken(email, password)
            .doOnSubscribe { authStatePublisher.onNext(AuthState.RefreshingForeground) }
            .flatMapCompletable {
                authLocalDataSource.saveToken(it)
            }
            .doOnComplete {
                authStatePublisher.onNext(AuthState.Authorized)
            } as Completable


    override fun signOut() = authRemoteDataSource
        .endSession(authLocalDataSource.getSessionId())
        .doOnSubscribe { authStatePublisher.onNext(AuthState.UnAuthorized) }
        .andThen { authLocalDataSource.deleteToken() } as Completable

    override fun getAuthState() = authStatePublisher
}