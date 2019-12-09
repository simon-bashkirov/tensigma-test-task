package com.testtask.data.remote.rest.adapter

import com.testtask.data.repository.auth.AuthTokenProvider

interface RestAdapter {

    fun <Service> createUnauthorizedService(serviceClass: Class<Service>): Service

    fun <Service> createAuthorizedService(
        serviceClass: Class<Service>,
        authTokenProvider: AuthTokenProvider
    ): Service

}