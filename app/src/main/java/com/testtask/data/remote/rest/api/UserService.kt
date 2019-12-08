package com.testtask.data.remote.rest.api

import com.testtask.data.remote.rest.model.response.UserInfoResponse
import io.reactivex.Single
import retrofit2.http.GET

interface UserService {

    @GET("accounts/current")
    fun getCurrentAccount(): Single<UserInfoResponse>
}