package com.testtask.data.remote.rest.api

import com.testtask.domain.model.user.UserInfo
import io.reactivex.Single
import retrofit2.http.GET

interface UserService {

    //TODO use data-level models
    @GET("accounts/current")
    fun getCurrentAccount(): Single<UserInfo>
}