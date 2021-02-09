package com.testtask.data.remote.rest.service

import com.testtask.data.remote.rest.model.request.SignInRequest
import com.testtask.data.remote.rest.model.response.TokenResponse
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.POST

interface UnauthorizedApiService {

    @POST("accounts/auth")
    fun signIn(@Body signInRequest: SignInRequest): Single<TokenResponse>

}