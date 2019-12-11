package com.testtask.data.remote.wss.mapper

import com.testtask.data.util.Optional
import com.testtask.domain.mapper.Mapper

interface MessageMapper<Message> : Mapper<String, Optional<Message>> {

    override fun map(source: String): Optional<Message>

}