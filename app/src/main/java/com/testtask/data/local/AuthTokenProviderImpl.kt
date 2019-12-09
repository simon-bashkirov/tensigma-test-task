package com.testtask.data.local

import com.testtask.data.repository.auth.AuthTokenProvider
import com.testtask.data.repository.auth.AuthLocalDataSource

class AuthTokenProviderImpl(private val authLocalSource: AuthLocalDataSource) : AuthTokenProvider {
    override fun getToken() = authLocalSource.getToken()
}