package com.testtask.data.remote.wss.adapter

import io.reactivex.Flowable

interface WebSocketAdapter {

    fun connect()

    fun disconnect()

    fun getMessageStream(): Flowable<String>
}