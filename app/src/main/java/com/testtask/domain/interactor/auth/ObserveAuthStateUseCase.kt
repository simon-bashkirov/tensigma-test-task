package com.testtask.domain.interactor.auth

import com.testtask.domain.interactor.FlowableInteractor
import com.testtask.domain.interactor.NoParams
import com.testtask.domain.model.user.AuthState
import com.testtask.domain.repository.AuthRepository

class ObserveAuthStateUseCase(private val authRepository: AuthRepository) :
    FlowableInteractor<NoParams, AuthState> {

    override fun invoke(params: NoParams) =
        authRepository.getAuthState()

}