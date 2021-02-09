package com.testtask.data.remote.rest.service

interface ApiServiceFactory {

    fun getUnauthorizedService(): UnauthorizedApiService

    fun getAuthorizedService(): AuthorizedApiService

}