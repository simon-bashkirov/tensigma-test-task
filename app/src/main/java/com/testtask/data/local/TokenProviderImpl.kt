package com.testtask.data.local

import com.testtask.data.repository.auth.TokenProvider
import com.testtask.data.repository.auth.TokenStorage

class TokenProviderImpl(private val tokenStorage: TokenStorage) : TokenProvider {
    override fun getToken() = tokenStorage.getToken()
}