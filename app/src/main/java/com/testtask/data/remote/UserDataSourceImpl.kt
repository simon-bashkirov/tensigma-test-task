package com.testtask.data.remote

import com.testtask.data.remote.rest.adapter.RestAdapter
import com.testtask.data.remote.rest.api.UserService
import com.testtask.data.remote.rest.mapper.UserInfoResponseMapper
import com.testtask.data.repository.auth.TokenProvider
import com.testtask.data.repository.user.UserDataSource
import com.testtask.domain.model.user.UserInfo
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class UserDataSourceImpl(restAdapter: RestAdapter, tokenProvider: TokenProvider) :
    UserDataSource {

    private val userService =
        restAdapter.createAuthorizedService(UserService::class.java, tokenProvider)


    override fun getCurrentUser(): Single<UserInfo> {
        return userService.getCurrentAccount()
            .map { UserInfoResponseMapper.map(it) }
            .subscribeOn(Schedulers.io())
    }
}