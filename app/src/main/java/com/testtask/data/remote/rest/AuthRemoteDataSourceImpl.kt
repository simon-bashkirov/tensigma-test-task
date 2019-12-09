package com.testtask.data.remote.rest

import com.testtask.data.remote.rest.adapter.RestAdapter
import com.testtask.data.remote.rest.api.AuthService
import com.testtask.data.remote.rest.model.request.SignInRequest
import com.testtask.data.remote.rest.model.request.SignOutRequest
import com.testtask.data.repository.auth.AuthRemoteDataSource
import com.testtask.data.repository.auth.AuthTokenProvider
import io.reactivex.schedulers.Schedulers

class AuthRemoteDataSourceImpl(restAdapter: RestAdapter, authTokenProvider: AuthTokenProvider) :
    AuthRemoteDataSource {

    private val signInService =
        restAdapter.createUnauthorizedService(AuthService.SignInService::class.java)

    private val signOutService = restAdapter.createAuthorizedService(
        AuthService.SignOutService::class.java,
        authTokenProvider
    )

    private val refreshTokenService = restAdapter.createAuthorizedService(
        AuthService.RefreshTokenService::class.java,
        authTokenProvider
    )

    override fun requestToken(email: String, password: String) =
        signInService.signIn(SignInRequest(email, password))
            .map { it.token }
            .subscribeOn(Schedulers.io())

    override fun endSession(sessionId: String) =
        signOutService.signOut(SignOutRequest(sessionId))
            .subscribeOn(Schedulers.io())

    override fun refreshToken() = refreshTokenService.refreshToken()
        .map { it.token }
        .subscribeOn(Schedulers.io())

}