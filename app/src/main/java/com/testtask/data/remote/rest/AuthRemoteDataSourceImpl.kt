package com.testtask.data.remote.rest

import com.testtask.data.remote.rest.model.request.SignInRequest
import com.testtask.data.remote.rest.model.request.SignOutRequest
import com.testtask.data.remote.rest.service.ApiServiceFactory
import com.testtask.data.repository.auth.AuthRemoteDataSource
import io.reactivex.schedulers.Schedulers

class AuthRemoteDataSourceImpl(apiServiceFactory: ApiServiceFactory) :
    AuthRemoteDataSource {

    private val authorizedService = apiServiceFactory.getAuthorizedService()

    private val unAuthorizedService = apiServiceFactory.getUnauthorizedService()

    override fun requestToken(email: String, password: String) =
        unAuthorizedService.signIn(SignInRequest(email, password))
            .map { it.token }
            .subscribeOn(Schedulers.io())

    override fun endSession(sessionId: String) =
        authorizedService.signOut(SignOutRequest(sessionId))
            .subscribeOn(Schedulers.io())

    override fun refreshToken() = authorizedService.refreshToken()
        .map { it.token }
        .subscribeOn(Schedulers.io())

}