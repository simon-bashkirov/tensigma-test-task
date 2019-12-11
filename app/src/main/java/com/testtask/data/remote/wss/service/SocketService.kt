package com.testtask.data.remote.wss.service

import io.reactivex.Flowable

interface SocketService<Command, Message> {

    fun sendCommand(command: Command)

    fun getMessageStream(): Flowable<Message>
}