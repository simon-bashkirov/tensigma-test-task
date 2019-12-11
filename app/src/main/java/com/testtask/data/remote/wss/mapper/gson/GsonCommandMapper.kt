package com.testtask.data.remote.wss.mapper.gson

import com.google.gson.Gson
import com.testtask.data.remote.wss.mapper.CommandMapper

class GsonCommandMapper<Command>(private val commandClass: Class<Command>) :
    CommandMapper<Command> {

    private val gson = Gson()

    override fun map(source: Command) = gson.toJson(source, commandClass) as String

}