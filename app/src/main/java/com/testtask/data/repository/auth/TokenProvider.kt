package com.testtask.data.repository.auth


interface TokenProvider {

    fun getToken(): String

}