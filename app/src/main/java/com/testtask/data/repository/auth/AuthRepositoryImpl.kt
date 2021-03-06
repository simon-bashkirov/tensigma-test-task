package com.testtask.data.repository.auth

import android.annotation.SuppressLint
import com.testtask.domain.model.user.AuthState
import com.testtask.domain.repository.AuthRepository
import io.reactivex.Completable
import io.reactivex.processors.BehaviorProcessor
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledFuture
import java.util.concurrent.TimeUnit

class AuthRepositoryImpl(
    private val authRemoteDataSource: AuthRemoteDataSource,
    private val authLocalDataSource: AuthLocalDataSource,
    private val authLostObserver: AuthLostObserver

) : AuthRepository {

    private val refreshTokenExecutor = Executors.newSingleThreadScheduledExecutor()
    private var refreshTask: ScheduledFuture<*>? = null

    private val authStatePublisher = BehaviorProcessor.create<AuthState>()

    init {
        if (authLocalDataSource.hasToken()) {
            refreshToken()
        } else {
            setUnauthorized()
        }
    }

    override fun signIn(email: String, password: String) =
        authRemoteDataSource.requestToken(email, password)
            .doOnSubscribe { authStatePublisher.onNext(AuthState.RefreshingForeground) }
            .flatMapCompletable { authLocalDataSource.saveToken(it) }
            .doOnComplete { setAuthorized() } as Completable


    override fun signOut() = authRemoteDataSource
        .endSession(authLocalDataSource.getSessionId())
        .doOnSubscribe {
            refreshTask?.cancel(false)
            setUnauthorized()
        }
        .andThen(authLocalDataSource.deleteToken()) as Completable

    override fun getAuthState() = authStatePublisher


    private fun setAuthorized() {
        authStatePublisher.onNext(AuthState.Authorized)
        scheduleNextRefresh(authLocalDataSource.refreshDelay())
    }

    private fun setUnauthorized() {
        authStatePublisher.onNext(AuthState.UnAuthorized)
        authLostObserver.onAuthLost().subscribe()
    }

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
            .subscribe({
                setAuthorized()
            }, {
                setUnauthorized()
            })
    }

}