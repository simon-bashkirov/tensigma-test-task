package com.testtask.data.remote.wss.service

interface SocketServiceFactory {

    fun <Command, Message> createSocketService(
        commandClass: Class<Command>,
        messageClass: Class<Message>
    )
            : SocketService<Command, Message>

}