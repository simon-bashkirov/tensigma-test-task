package com.testtask.domain.interactor.user

import com.testtask.domain.interactor.FlowableInteractor
import com.testtask.domain.model.user.Profile
import com.testtask.domain.repository.UserRepository

class ObserveMyFirstProfileUseCase(private val userRepository: UserRepository) :
    FlowableInteractor<Nothing, Profile> {

    override fun execute(params: Nothing) =
        userRepository.getCurrentUserInfo().map { it.profiles.first() }
}