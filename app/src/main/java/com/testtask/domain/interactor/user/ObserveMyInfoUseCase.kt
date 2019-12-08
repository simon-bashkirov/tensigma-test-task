package com.testtask.domain.interactor.user

import com.testtask.domain.interactor.FlowableInteractor
import com.testtask.domain.model.user.UserInfo
import com.testtask.domain.repository.UserRepository

class ObserveMyInfoUseCase(private val userRepository: UserRepository) :
    FlowableInteractor<Nothing, UserInfo> {

    override fun execute(params: Nothing) = userRepository.getCurrentUserInfo()
}