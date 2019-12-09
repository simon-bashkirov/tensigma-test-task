package com.testtask.data.repository.auth


interface AuthTokenProvider {

    fun getToken(): String

}