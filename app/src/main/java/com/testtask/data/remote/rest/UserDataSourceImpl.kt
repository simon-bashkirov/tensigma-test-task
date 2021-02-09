package com.testtask.data.remote.rest

import com.testtask.data.remote.rest.mapper.UserInfoResponseMapper
import com.testtask.data.remote.rest.service.ApiServiceFactory
import com.testtask.data.repository.user.UserDataSource
import com.testtask.domain.model.user.UserInfo
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class UserDataSourceImpl(apiServiceFactory: ApiServiceFactory) :
    UserDataSource {

    private val authorizedService = apiServiceFactory.getAuthorizedService()

    override fun getCurrentUser(): Single<UserInfo> {
        return authorizedService.getCurrentAccount()
            .map { UserInfoResponseMapper.map(it) }
            .subscribeOn(Schedulers.io())
    }
}