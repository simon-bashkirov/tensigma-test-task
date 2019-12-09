package com.testtask.domain.interactor.transaction

import com.testtask.domain.interactor.FlowableInteractor
import com.testtask.domain.interactor.NoParams
import com.testtask.domain.model.transaction.Transaction
import com.testtask.domain.repository.TransactionRepository


class ObserveTransactionUpdatesUseCase(private val transactionRepository: TransactionRepository) :
    FlowableInteractor<NoParams, List<Transaction>> {

    override fun execute(params: NoParams) = transactionRepository.getTransactionListUpdates()

}