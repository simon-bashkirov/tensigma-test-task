package com.testtask.data.remote.wss.service.impl

import android.annotation.SuppressLint
import com.testtask.data.remote.wss.client.SocketClient
import com.testtask.data.remote.wss.mapper.CommandMapper
import com.testtask.data.remote.wss.mapper.MessageMapper
import com.testtask.data.remote.wss.service.SocketService
import io.reactivex.Flowable
import io.reactivex.processors.PublishProcessor

class SocketServiceImpl<Command, Message>(

    private val socketClient: SocketClient,

    private val commandMapper: CommandMapper<Command>,

    private val messageMapper: MessageMapper<Message>

) : SocketService<Command, Message> {

    private val messagePublisher = PublishProcessor.create<Message>()

    init {
        socketClient.connect()
        subscribeToStream()
    }

    override fun sendCommand(command: Command) {
        socketClient.sendRawMessage(commandMapper.map(command))
    }

    override fun getMessageStream() = messagePublisher as Flowable<Message>


    @SuppressLint("CheckResult")
    private fun subscribeToStream() {
        socketClient.getRawMessageStream()
            .map { messageMapper.map(it) }
            .subscribe({ (message) ->
                message?.let { messagePublisher.onNext(it) }
            }, {
                //TODO
            })

    }
}