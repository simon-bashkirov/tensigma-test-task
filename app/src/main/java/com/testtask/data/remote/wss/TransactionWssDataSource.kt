package com.testtask.data.remote.wss

import com.testtask.data.remote.wss.adapter.WebSocketAdapter
import com.testtask.data.repository.transaction.TransactionDataSource
import com.testtask.domain.model.transaction.Transaction
import io.reactivex.Completable
import io.reactivex.Flowable

class TransactionWssDataSource(private val webSocketAdapter: WebSocketAdapter) :
    TransactionDataSource {
    override fun startTransactionStream(): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun stopTransactionStream(): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getTransactionStream(): Flowable<Transaction> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}