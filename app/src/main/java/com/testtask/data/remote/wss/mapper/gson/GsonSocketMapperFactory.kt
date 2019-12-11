package com.testtask.data.remote.wss.mapper.gson

import com.testtask.data.remote.wss.mapper.SocketMapperFactory

class GsonSocketMapperFactory : SocketMapperFactory {

    override fun <Command> createCommandMapper(commandClass: Class<Command>) =
        GsonCommandMapper(commandClass)

    override fun <Message> createMessageMapper(messageClass: Class<Message>) =
        GsonMessageMapper(messageClass)
}