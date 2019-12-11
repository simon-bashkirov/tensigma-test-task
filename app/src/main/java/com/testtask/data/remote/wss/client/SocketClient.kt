package com.testtask.data.remote.wss.client

import io.reactivex.Flowable

interface SocketClient {

    fun connect()

    fun disconnect()

    fun getRawMessageStream(): Flowable<String>

    fun sendRawMessage(string: String)
}