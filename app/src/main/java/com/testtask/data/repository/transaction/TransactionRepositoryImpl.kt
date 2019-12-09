package com.testtask.data.repository.transaction

import com.testtask.domain.model.transaction.Transaction
import com.testtask.domain.repository.TransactionRepository
import io.reactivex.Completable
import io.reactivex.Flowable

class TransactionRepositoryImpl(private val transactionDataSource: TransactionDataSource) :
    TransactionRepository {

    override fun startTransactionStream(): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun stopTransactionStream(): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun clearTransactionCache(): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getTransactionListUpdates(): Flowable<List<Transaction>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}