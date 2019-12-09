package com.testtask.data.local

import com.testtask.data.repository.auth.AuthLostObserver
import com.testtask.domain.repository.TransactionRepository
import com.testtask.domain.repository.UserRepository

class AuthLostObserverImpl(
    private val userRepository: UserRepository,
    private val transactionRepository: TransactionRepository

) : AuthLostObserver {

    override fun onAuthLost() =
        userRepository.clearCurrentUser()
            .andThen { transactionRepository.stopTransactionStream() }
            .andThen { transactionRepository.clearTransactionCache() }

}