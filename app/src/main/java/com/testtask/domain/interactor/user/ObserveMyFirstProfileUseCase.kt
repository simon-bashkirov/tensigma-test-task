package com.testtask.domain.interactor.user

import com.testtask.domain.interactor.FlowableInteractor
import com.testtask.domain.interactor.NoParams
import com.testtask.domain.model.user.Profile
import com.testtask.domain.repository.UserRepository
import com.testtask.utils.lang.Optional

class ObserveMyFirstProfileUseCase(private val userRepository: UserRepository) :
    FlowableInteractor<NoParams, Optional<Profile>> {

    override fun invoke(params: NoParams) =
        userRepository.getCurrentUserInfo()
            .map { Optional(it.value?.profiles?.firstOrNull()) }

}