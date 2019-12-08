package com.testtask.data.remote.rest.api

import com.testtask.data.remote.rest.model.request.SignInRequest
import com.testtask.data.remote.rest.model.response.TokenResponse
import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthService {

    interface SignInService {

        @POST("accounts/auth")
        fun signIn(@Body signInRequest: SignInRequest): Single<TokenResponse>
    }

    interface SignOutService {
        @GET("accounts/sessions/end")
        fun signOut(): Completable
    }

    interface RefreshTokenService {

        @GET("accounts/sessions/refresh")
        fun refreshToken(): Single<TokenResponse>
    }
}
