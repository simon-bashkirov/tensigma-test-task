package com.testtask.data.repository.auth

import android.annotation.SuppressLint
import com.testtask.domain.repository.AuthRepository
import com.testtask.domain.state.AuthState
import io.reactivex.Completable
import io.reactivex.processors.BehaviorProcessor
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledFuture
import java.util.concurrent.TimeUnit

class AuthRepositoryImpl(
    private val authRemoteDataSource: AuthRemoteDataSource,
    private val authLocalDataSource: AuthLocalDataSource
) : AuthRepository {

    private val refreshTokenExecutor = Executors.newSingleThreadScheduledExecutor()
    private var refreshTask: ScheduledFuture<*>? = null

    private val authStatePublisher = BehaviorProcessor.create<AuthState>()

    init {
        if (authLocalDataSource.hasToken()) {
            refreshToken()
        } else {
            authStatePublisher.onNext(AuthState.UnAuthorized)
        }
    }

    override fun signIn(email: String, password: String) =
        authRemoteDataSource.requestToken(email, password)
            .doOnSubscribe { authStatePublisher.onNext(AuthState.RefreshingForeground) }
            .flatMapCompletable { authLocalDataSource.saveToken(it) }
            .doOnComplete {
                scheduleNextRefresh(authLocalDataSource.refreshDelay())
                authStatePublisher.onNext(AuthState.Authorized)
            } as Completable


    override fun signOut() = authRemoteDataSource
        .endSession(authLocalDataSource.getSessionId())
        .doOnSubscribe {
            refreshTask?.cancel(false)
            authStatePublisher.onNext(AuthState.UnAuthorized)
        }
        .doOnComplete { authLocalDataSource.deleteToken() } as Completable

    override fun getAuthState() = authStatePublisher


    private fun scheduleNextRefresh(delayMs: Long) {
        val validDelay = if (delayMs < 0) 0 else delayMs
        refreshTask = refreshTokenExecutor.schedule(
            { refreshToken() }, validDelay, TimeUnit.MILLISECONDS
        )
    }

    @SuppressLint("CheckResult")
    private fun refreshToken() {
        authRemoteDataSource.refreshToken()
            .doOnSubscribe { authStatePublisher.onNext(AuthState.RefreshingBackground) }
            .flatMapCompletable { authLocalDataSource.saveToken(it) }
            .andThen { scheduleNextRefresh(authLocalDataSource.refreshDelay()) }
            .subscribe({
                authStatePublisher.onNext(AuthState.Authorized)
            }, {
                authStatePublisher.onNext(AuthState.UnAuthorized)
            })
    }
}