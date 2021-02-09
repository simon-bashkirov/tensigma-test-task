package com.testtask.domain.interactor.auth

import com.testtask.domain.interactor.CompletableInteractor
import com.testtask.domain.interactor.NoParams
import com.testtask.domain.repository.AuthRepository

class SignOutUseCase(private val authRepository: AuthRepository) :
    CompletableInteractor<NoParams> {

    override fun invoke(params: NoParams) =
        authRepository.signOut()

}