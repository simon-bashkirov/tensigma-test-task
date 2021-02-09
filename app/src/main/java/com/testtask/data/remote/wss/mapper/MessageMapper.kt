package com.testtask.data.remote.wss.mapper

import com.testtask.domain.mapper.Mapper
import com.testtask.utils.lang.Optional

interface MessageMapper<Message> : Mapper<String, Optional<Message>> {

    override fun map(source: String): Optional<Message>

}