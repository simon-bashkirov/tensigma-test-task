package com.testtask.data.remote.wss.service.impl

import com.testtask.data.remote.wss.client.SocketClient
import com.testtask.data.remote.wss.mapper.SocketMapperFactory
import com.testtask.data.remote.wss.service.SocketServiceFactory

class SocketServiceFactoryImpl(

    private val socketClient: SocketClient,

    private val mapperFactory: SocketMapperFactory

) : SocketServiceFactory {

    override fun <Command, Message> createSocketService(
        commandClass: Class<Command>,
        messageClass: Class<Message>
    ) = SocketServiceImpl(
        socketClient = socketClient,
        commandMapper = mapperFactory.createCommandMapper(commandClass),
        messageMapper = mapperFactory.createMessageMapper(messageClass)
    )
}