package com.testtask.data.remote.rest.adapter

import com.testtask.data.repository.auth.TokenProvider

interface RestAdapter {

    fun <Service> createUnauthorizedService(serviceClass: Class<Service>): Service

    fun <Service> createAuthorizedService(
        serviceClass: Class<Service>,
        tokenProvider: TokenProvider
    ): Service

}