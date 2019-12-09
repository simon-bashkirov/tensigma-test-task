package com.testtask.domain.repository

import com.testtask.domain.model.transaction.Transaction
import io.reactivex.Completable
import io.reactivex.Flowable

interface TransactionRepository {

    fun startTransactionStream(): Completable

    fun stopTransactionStream(): Completable

    fun clearTransactionCache(): Completable

    fun getTransactionListUpdates(): Flowable<List<Transaction>>
}