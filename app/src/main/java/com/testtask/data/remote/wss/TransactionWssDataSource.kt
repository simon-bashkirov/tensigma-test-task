package com.testtask.data.remote.wss

import com.testtask.data.remote.wss.adapter.WebSocketAdapter
import com.testtask.data.repository.transaction.TransactionDataSource

class TransactionWssDataSource(private val webSocketAdapter: WebSocketAdapter) :
    TransactionDataSource {

}