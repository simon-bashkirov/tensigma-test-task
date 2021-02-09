package com.testtask.domain.interactor.auth

import com.testtask.domain.interactor.FlowableInteractor
import com.testtask.domain.interactor.NoParams
import com.testtask.domain.repository.AuthRepository
import com.testtask.domain.state.AuthState

class ObserveAuthStateUseCase(private val authRepository: AuthRepository) :
    FlowableInteractor<NoParams, AuthState> {

    override fun invoke(params: NoParams) =
        authRepository.getAuthState()

}