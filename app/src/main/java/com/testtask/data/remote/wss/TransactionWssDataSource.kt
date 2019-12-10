package com.testtask.data.remote.wss

import com.google.gson.Gson
import com.testtask.data.remote.wss.adapter.WebSocketAdapter
import com.testtask.data.remote.wss.mapper.TransactionMessageMapper
import com.testtask.data.remote.wss.model.command.SocketCommand
import com.testtask.data.remote.wss.model.command.SocketCommandType.SUBSCRIBE
import com.testtask.data.remote.wss.model.command.SocketCommandType.UNSUBSCRIBE
import com.testtask.data.remote.wss.model.message.TransactionMessage
import com.testtask.data.repository.transaction.TransactionDataSource
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
        webSocketAdapter.sendMessage(
            gson.toJson(
                SocketCommand(
                    SUBSCRIBE
                )
            ).toString()
        )
    }

    override fun stopTransactionStream() = Completable.fromCallable {
        webSocketAdapter.sendMessage(
            gson.toJson(
                SocketCommand(
                    UNSUBSCRIBE
                )
            ).toString()
        )
    }

    override fun getTransactionStream() = webSocketAdapter.getMessageStream()
        .map { gson.fromJson(it, TransactionMessage::class.java) }
        .map { TransactionMessageMapper.map(it) }
}