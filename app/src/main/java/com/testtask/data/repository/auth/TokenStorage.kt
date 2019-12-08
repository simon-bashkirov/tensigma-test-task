package com.testtask.data.repository.auth

import io.reactivex.Completable

interface TokenStorage : TokenProvider {

    fun saveToken(newToken: String): Completable

    fun deleteToken(): Completable

    fun hasToken(): Boolean

    fun expiringSoon(): Boolean

    fun isExpired(): Boolean

    fun getSessionId(): String
}