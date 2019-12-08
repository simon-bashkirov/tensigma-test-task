package com.testtask.data.repository

import io.reactivex.Completable

interface TokenProvider {

    fun getToken(): String

    fun refreshToken(newToken: String): Completable

    fun deleteToken(): Completable
}