package com.testtask.domain.interactor.transaction

import com.testtask.domain.interactor.CompletableInteractor
import com.testtask.domain.interactor.NoParams
import com.testtask.domain.repository.TransactionRepository


class StartTransactionsUseCase(private val transactionRepository: TransactionRepository) :
    CompletableInteractor<NoParams> {

    override fun execute(params: NoParams) = transactionRepository.startTransactionStream()

}