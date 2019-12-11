package com.testtask.data.remote.wss.client

import com.testtask.data.remote.wss.client.state.ConnectionState
import io.reactivex.Flowable

interface SocketClient {

    fun connect()

    fun disconnect()

    fun getRawMessageStream(): Flowable<String>

    fun sendRawMessage(string: String)

    fun connectionState(): Flowable<ConnectionState>
}