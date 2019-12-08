package com.testtask.domain.repository

import com.testtask.domain.state.AuthState
import io.reactivex.Completable
import io.reactivex.Flowable

interface AuthRepository {

    fun signIn(email: String, password: String): Completable

    fun signOut(): Completable

    fun getAuthState(): Flowable<AuthState>
}