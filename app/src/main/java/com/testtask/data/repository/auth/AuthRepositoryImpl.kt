package com.testtask.data.repository.auth

import com.testtask.domain.repository.AuthRepository
import com.testtask.domain.state.AuthState
import io.reactivex.Completable
import io.reactivex.processors.BehaviorProcessor

class AuthRepositoryImpl(
    private val authDataSource: AuthDataSource,
    private val tokenStorage: TokenStorage
) : AuthRepository {

    private val authStatePublisher = BehaviorProcessor.create<AuthState>()
        .apply {
            if (tokenStorage.hasToken() && !tokenStorage.isExpired()) {
                onNext(AuthState.Authorized)
            } else {
                onNext(AuthState.UnAuthorized)
            }
        }

    override fun signIn(email: String, password: String) =
        authDataSource.requestToken(email, password)
            .doOnSubscribe { authStatePublisher.onNext(AuthState.RefreshingForeground) }
            .flatMapCompletable {
                tokenStorage.saveToken(it)
            }
            .doOnComplete {
                authStatePublisher.onNext(AuthState.Authorized)
            } as Completable


    override fun signOut() = authDataSource
        .endSession(tokenStorage.getSessionId())
        .andThen { tokenStorage.deleteToken() }
        .doOnComplete {
            authStatePublisher.onNext(AuthState.UnAuthorized)
        } as Completable

    override fun getAuthState() = authStatePublisher
}