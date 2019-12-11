package com.testtask.data.remote.wss.mapper

import com.testtask.domain.mapper.Mapper

interface CommandMapper<Command> : Mapper<Command, String> {

    override fun map(source: Command): String

}