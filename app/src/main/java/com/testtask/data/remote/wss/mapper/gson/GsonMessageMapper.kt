package com.testtask.data.remote.wss.mapper.gson

import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.testtask.data.remote.wss.mapper.MessageMapper
import com.testtask.data.util.Optional

class GsonMessageMapper<Message>(private val messageClass: Class<Message>) :
    MessageMapper<Message> {

    private val gson = Gson()

    override fun map(source: String): Optional<Message> {

        return try {
            Optional(gson.fromJson(source, messageClass))
        } catch (ex: JsonSyntaxException) {
            Optional()
        }
    }

}