package com.testtask.domain.interactor.user

import com.testtask.data.util.Optional
import com.testtask.domain.interactor.FlowableInteractor
import com.testtask.domain.interactor.user.ObserveMyFirstProfileUseCase.NoParams
import com.testtask.domain.model.user.Profile
import com.testtask.domain.repository.UserRepository

class ObserveMyFirstProfileUseCase(private val userRepository: UserRepository) :
    FlowableInteractor<NoParams, Optional<Profile>> {

    override fun execute(params: NoParams) =
        userRepository.getCurrentUserInfo()
            .map { Optional(it.value?.profiles?.firstOrNull()) }

    object NoParams
}