package com.testtask.data.remote

import com.testtask.data.remote.rest.adapter.RestAdapter
import com.testtask.data.remote.rest.api.AuthService
import com.testtask.data.remote.rest.model.request.SignInRequest
import com.testtask.data.repository.AuthDataSource
import com.testtask.data.repository.TokenProvider
import io.reactivex.schedulers.Schedulers

class AuthDataSourceImpl(restAdapter: RestAdapter, private val tokenProvider: TokenProvider) :
    AuthDataSource {

    private val signInService =
        restAdapter.createUnauthorizedService(AuthService.SignInService::class.java)

    private val signOutService = restAdapter.createAuthorizedService(
        AuthService.SignOutService::class.java,
        tokenProvider
    )

    private val refreshTokenService = restAdapter.createAuthorizedService(
        AuthService.RefreshTokenService::class.java,
        tokenProvider
    )

    override fun signIn(email: String, password: String) =
        signInService.signIn(SignInRequest(email, password))
            .flatMapCompletable {
                tokenProvider.refreshToken(it.token)
            }
            .subscribeOn(Schedulers.io())

    override fun signOut() = signOutService
        .signOut()
        .doOnComplete { tokenProvider.deleteToken() }
        .subscribeOn(Schedulers.io())

    override fun refreshToken() = refreshTokenService.refreshToken()
        .flatMapCompletable { tokenProvider.refreshToken(it.token) }
        .subscribeOn(Schedulers.io())

}