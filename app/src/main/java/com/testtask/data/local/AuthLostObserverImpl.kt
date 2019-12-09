package com.testtask.data.local

import com.testtask.data.repository.auth.AuthLostObserver
import com.testtask.domain.repository.UserRepository

class AuthLostObserverImpl(private val userRepository: UserRepository) : AuthLostObserver {

    override fun onAuthLost() =
        userRepository.clearCurrentUser()

}