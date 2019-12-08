package com.testtask.data.repository

import com.testtask.domain.repository.AuthRepository
import com.testtask.domain.state.AuthState
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers

class AuthRepositoryImpl : AuthRepository {
    override fun signIn(login: String, password: String): Completable {
        //TODO in progress
        return Completable.complete()
    }

    override fun signOut(): Completable {
        //TODO in progress
        return Completable.complete()
    }

    override fun getAuthState(): Flowable<AuthState> {
        //TODO in progress
        return Flowable.just(
            AuthState.UnAuthorized as AuthState
        )
            .subscribeOn(Schedulers.single())
    }
}