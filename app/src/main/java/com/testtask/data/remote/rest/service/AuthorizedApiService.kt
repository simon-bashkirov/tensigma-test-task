package com.testtask.data.remote.rest.service

import com.testtask.data.remote.rest.model.request.SignOutRequest
import com.testtask.data.remote.rest.model.response.TokenResponse
import com.testtask.data.remote.rest.model.response.UserInfoResponse
import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthorizedApiService {

    @POST("accounts/sessions/refresh")
    fun refreshToken(): Single<TokenResponse>

    @POST("accounts/sessions/end")
    fun signOut(@Body signOutRequest: SignOutRequest): Completable

    @GET("accounts/current")
    fun getCurrentAccount(): Single<UserInfoResponse>
}