package com.testtask.data.remote.wss.service

import com.testtask.data.remote.wss.client.state.ConnectionState
import io.reactivex.Flowable

interface SocketService<Command, Message> {

    fun connect()

    fun disconnect()

    fun connectionState(): Flowable<ConnectionState>

    fun sendCommand(command: Command)

    fun getMessageStream(): Flowable<Message>
}