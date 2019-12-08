package com.testtask.domain.interactor.auth

import com.testtask.domain.interactor.CompletableInteractor
import com.testtask.domain.repository.AuthRepository

class SignOutUseCase(private val authRepository: AuthRepository) :
    CompletableInteractor<Nothing> {

    override fun execute(params: Nothing) =
        authRepository.signOut()

}