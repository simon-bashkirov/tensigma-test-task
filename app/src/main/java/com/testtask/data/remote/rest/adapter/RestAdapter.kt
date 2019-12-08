package com.testtask.data.remote.rest.adapter

import com.testtask.data.remote.rest.TokenProvider
import com.testtask.data.remote.rest.api.base.AuthorizedService
import com.testtask.data.remote.rest.api.base.UnauthorizedService

interface RestAdapter {

    fun <Service : UnauthorizedService> createUnauthorizedService(serviceClass: Class<Service>): Service

    fun <Service : AuthorizedService> createAuthorizedService(
        serviceClass: Class<Service>,
        tokenProvider: TokenProvider
    ): Service

}