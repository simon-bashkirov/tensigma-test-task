package com.testtask.data.local

import com.testtask.data.repository.auth.TokenProvider
import io.reactivex.Completable

class TokenProviderImpl : TokenProvider {

    private var token: String = "" //todo save to storage

    override fun getToken() = token

    override fun saveToken(newToken: String): Completable {
        token = newToken
        return Completable.complete()
    }

    override fun deleteToken(): Completable {
        token = ""
        return Completable.complete()
    }

    override fun hasToken(): Boolean {
        return token.isNotEmpty()
    }
}