package com.testtask.data.repository.auth

import io.reactivex.Completable

interface TokenProvider {

    fun getToken(): String

    fun saveToken(newToken: String): Completable

    fun deleteToken(): Completable

    fun hasToken(): Boolean
}