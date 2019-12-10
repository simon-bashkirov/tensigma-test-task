package com.testtask.data.remote.wss

import com.google.gson.Gson
import com.testtask.data.remote.wss.adapter.WebSocketAdapter
import com.testtask.data.remote.wss.model.SocketCommand
import com.testtask.data.remote.wss.model.SocketCommand.SUBSCRIBE
import com.testtask.data.remote.wss.model.SocketCommand.UNSUBSCRIBE
import com.testtask.data.remote.wss.model.SocketRequest
import com.testtask.data.repository.transaction.TransactionDataSource
import com.testtask.domain.model.transaction.Transaction
import io.reactivex.Completable

class TransactionWssDataSource(
    private val webSocketAdapter: WebSocketAdapter
) :
    TransactionDataSource {

    init {
        webSocketAdapter.connect()
    }

    private val gson = Gson()

    override fun startTransactionStream() = Completable.fromCallable {
        webSocketAdapter.sendMessage(gson.toJson(SocketRequest(SUBSCRIBE)).toString())
    }

    override fun stopTransactionStream() = Completable.fromCallable {
        webSocketAdapter.sendMessage(gson.toJson(SocketRequest(UNSUBSCRIBE)).toString())
    }

    override fun getTransactionStream() = webSocketAdapter.getMessageStream()
        .map {
            gson.fromJson(it, Transaction::class.java)
        }
}