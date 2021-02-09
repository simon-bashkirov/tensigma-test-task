package com.testtask.domain.interactor.auth

import com.testtask.domain.interactor.CompletableInteractor
import com.testtask.domain.repository.AuthRepository

class SignInUseCase(private val authRepository: AuthRepository) :
    CompletableInteractor<SignInUseCase.Params> {

    override fun invoke(params: Params) =
        authRepository.signIn(
            email = params.email,
            password = params.password
        )

    data class Params(val email: String, val password: String)

}