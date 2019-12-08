package com.testtask.data.repository.auth

import io.reactivex.Completable

interface AuthDataSource {

    fun signIn(email: String, password: String): Completable

    fun signOut(): Completable

    fun refreshToken(): Completable
}