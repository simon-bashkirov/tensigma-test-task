package com.testtask.data.repository.transaction

import android.annotation.SuppressLint
import com.testtask.domain.model.transaction.Transaction
import com.testtask.domain.repository.TransactionRepository
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.processors.BehaviorProcessor

class TransactionRepositoryImpl(private val transactionDataSource: TransactionDataSource) :
    TransactionRepository {

    private val transactionUpdatesPublisher = BehaviorProcessor.create<List<Transaction>>()

    init {
        subscribeToTransactions()
    }

    override fun startTransactionStream() = transactionDataSource.startTransactionStream()

    override fun stopTransactionStream() = transactionDataSource.stopTransactionStream()

    override fun clearTransactionCache() = Completable.fromCallable {
        transactionUpdatesPublisher.onNext(emptyList())
    }

    override fun getTransactionListUpdates() =
        transactionUpdatesPublisher as Flowable<List<Transaction>>

    @SuppressLint("CheckResult")
    private fun subscribeToTransactions() {
        transactionDataSource.getTransactionStream()
            .subscribe({
                val list = transactionUpdatesPublisher.value ?: emptyList()
                transactionUpdatesPublisher.onNext(
                    ArrayList(list).apply {
                        add(0, it)
                    }
                )
            },
                {
                    transactionUpdatesPublisher.onError(it)
                })
    }
}