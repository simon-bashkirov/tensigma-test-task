package com.testtask.data.remote.wss.mapper

interface SocketMapperFactory {

    fun <Command> createCommandMapper(commandClass: Class<Command>): CommandMapper<Command>

    fun <Message> createMessageMapper(messageClass: Class<Message>): MessageMapper<Message>
}