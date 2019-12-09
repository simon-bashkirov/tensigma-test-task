package com.testtask.data.repository.auth

import io.reactivex.Completable

interface AuthLocalDataSource : AuthTokenProvider {

    fun saveToken(newToken: String): Completable

    fun deleteToken(): Completable

    fun hasToken(): Boolean

    fun refreshDelay(): Long

    fun isExpired(): Boolean

    fun getSessionId(): String
}