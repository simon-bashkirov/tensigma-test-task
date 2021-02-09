package com.testtask.domain.model.user

sealed class AuthState {
    object Authorized : AuthState()
    object UnAuthorized : AuthState()
    object RefreshingForeground : AuthState()
    object RefreshingBackground : AuthState()
}