package com.testtask.domain.state

sealed class AuthState {
    object Authorized : AuthState()
    object UnAuthorized : AuthState()
    object RefreshingForeground : AuthState()
    object RefreshingBackground : AuthState()
}