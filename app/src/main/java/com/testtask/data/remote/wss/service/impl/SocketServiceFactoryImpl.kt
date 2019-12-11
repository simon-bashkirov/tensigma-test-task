package com.testtask.data.remote.wss.service.impl

import com.testtask.data.remote.wss.mapper.SocketMapperFactory
import com.testtask.data.remote.wss.service.SocketServiceFactory

class SocketServiceFactoryImpl(

    private val wssBaseUrl: String,

    private val mapperFactory: SocketMapperFactory

) : SocketServiceFactory {

    override fun <Command, Message> createSocketService(
        commandClass: Class<Command>,
        messageClass: Class<Message>
    ) = ProxySocketService(
        hostSocketClient = OkHttpSocketService(wssBaseUrl),
        commandMapper = mapperFactory.createCommandMapper(commandClass),
        messageMapper = mapperFactory.createMessageMapper(messageClass)
    )
}