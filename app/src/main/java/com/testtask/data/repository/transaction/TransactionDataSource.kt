package com.testtask.data.repository.transaction

import com.testtask.domain.model.transaction.Transaction
import io.reactivex.Completable
import io.reactivex.Flowable

interface TransactionDataSource {

    fun startTransactionStream(): Completable

    fun stopTransactionStream(): Completable

    fun getTransactionStream(): Flowable<Transaction>

}