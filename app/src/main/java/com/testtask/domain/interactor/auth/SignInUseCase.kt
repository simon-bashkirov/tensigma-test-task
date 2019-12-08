package com.testtask.domain.interactor.auth

import com.testtask.domain.interactor.CompletableInteractor
import com.testtask.domain.repository.AuthRepository

class SignInUseCase(private val authRepository: AuthRepository) :
    CompletableInteractor<SignInUseCase.Params> {

    override fun execute(params: Params) =
        authRepository.signIn(
            login = params.login,
            password = params.password
        )

    data class Params(val login: String, val password: String)

}