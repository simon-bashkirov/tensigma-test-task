package com.testtask.data.remote.wss.service.impl

import android.annotation.SuppressLint
import com.testtask.data.remote.wss.mapper.CommandMapper
import com.testtask.data.remote.wss.mapper.MessageMapper
import com.testtask.data.remote.wss.service.SocketService
import io.reactivex.Flowable
import io.reactivex.processors.PublishProcessor
import io.reactivex.schedulers.Schedulers

class ProxySocketService<Command, Message>(

    private val hostSocketService: SocketService<String, String>,

    private val commandMapper: CommandMapper<Command>,

    private val messageMapper: MessageMapper<Message>

) : SocketService<Command, Message> {

    private val messagePublisher = PublishProcessor.create<Message>()

    init {
        hostSocketService.connect()
        subscribeToStream()
    }

    override fun connect() {
        hostSocketService.connect()
    }

    override fun disconnect() {
        hostSocketService.disconnect()
    }

    override fun connectionState() = hostSocketService.connectionState()

    override fun sendCommand(command: Command) {
        hostSocketService.sendCommand(commandMapper.map(command))
    }

    override fun getMessageStream() =
        (messagePublisher as Flowable<Message>).subscribeOn(Schedulers.io())


    @SuppressLint("CheckResult")
    private fun subscribeToStream() {
        hostSocketService.getMessageStream()
            .map { messageMapper.map(it) }
            .subscribe({ (message) ->
                message?.let { messagePublisher.onNext(it) }
            }, {
                //TODO
            })
    }
}