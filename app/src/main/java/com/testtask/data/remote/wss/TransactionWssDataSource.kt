package com.testtask.data.remote.wss

import com.google.gson.Gson
import com.testtask.data.remote.wss.adapter.WebSocketAdapter
import com.testtask.data.remote.wss.model.SocketRequest
import com.testtask.data.repository.transaction.TransactionDataSource
import com.testtask.domain.model.transaction.Transaction
import io.reactivex.Completable

class TransactionWssDataSource(
    private val webSocketAdapter: WebSocketAdapter,
    private val subscribeOp: String,
    private val unsubscribeOp: String
) :
    TransactionDataSource {

    init {
        webSocketAdapter.connect()
    }

    private val gson = Gson()

    override fun startTransactionStream() = Completable.fromCallable {
        webSocketAdapter.sendMessage(gson.toJson(SocketRequest(subscribeOp)).toString())
    }

    override fun stopTransactionStream() = Completable.fromCallable {
        webSocketAdapter.sendMessage(gson.toJson(SocketRequest(unsubscribeOp)).toString())
    }

    override fun getTransactionStream() = webSocketAdapter.getMessageStream()
        .map {
            gson.fromJson(it, Transaction::class.java)
        }
}