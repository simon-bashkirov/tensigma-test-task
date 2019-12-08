package com.testtask.data.local

import com.testtask.data.repository.TokenProvider
import io.reactivex.Completable

class TokenProviderImpl : TokenProvider {

    private var token: String = "" //todo save to storage

    override fun getToken() = token

    override fun refreshToken(newToken: String): Completable {
        token = newToken
        return Completable.complete()
    }

    override fun deleteToken(): Completable {
        token = ""
        return Completable.complete()
    }
}