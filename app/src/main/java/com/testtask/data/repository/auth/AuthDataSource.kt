package com.testtask.data.repository.auth

import io.reactivex.Completable
import io.reactivex.Single

interface AuthDataSource {

    fun requestToken(email: String, password: String): Single<String>

    fun endSession(sessionId: String): Completable

    fun refreshToken(): Single<String>
}