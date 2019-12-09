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
            .onBackpressureBuffer()
            .map {
                (transactionUpdatesPublisher.value ?: emptyList())
                    .toMutableList()
                    .apply {
                        add(it)
                        if (size > MAX_LIST_SIZE) remove(first())
                    }
                    .sortedBy { it.x?.time }
            }
            .subscribe({
                transactionUpdatesPublisher.onNext(it)
            }, {
                transactionUpdatesPublisher.onError(it)
            })
    }

    companion object {
        private const val MAX_LIST_SIZE = 10
    }
}